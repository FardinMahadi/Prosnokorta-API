package com.quiz;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quiz.model.Question;
import com.quiz.model.Quiz;
import com.quiz.model.Subject;
import com.quiz.repository.QuestionRepository;
import com.quiz.repository.QuizRepository;
import com.quiz.repository.SubjectRepository;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@Slf4j
public class OnlineQuizApplication {

    public static void main(String[] args) {
        log.info("Starting OnlineQuizApplication...");
        try {
            Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
            dotenv.entries().forEach(entry -> {
                if (System.getProperty(entry.getKey()) == null) {
                    System.setProperty(entry.getKey(), entry.getValue());
                }
            });
            log.info("Environment variables loaded successfully.");
        } catch (Exception e) {
            log.warn("Could not load .env file, relying on system environment variables: {}", e.getMessage());
        }
        
        SpringApplication.run(OnlineQuizApplication.class, args);
    }

    @Bean
    public CommandLineRunner seeder(
            SubjectRepository subjectRepository,
            QuizRepository quizRepository,
            QuestionRepository questionRepository,
            ObjectMapper objectMapper) {
        return args -> {
            log.info("--- STARTING DATABASE SEEDING ---");
            log.info("Checking for existing data...");
            seedSubject("Physics", "PHYS-10", "physics_seed.json", subjectRepository, quizRepository, questionRepository, objectMapper);
            seedSubject("Chemistry", "CHEM-10", "chemistry_seed.json", subjectRepository, quizRepository, questionRepository, objectMapper);
            log.info("--- DATABASE SEEDING COMPLETED ---");
        };
    }

    private void seedSubject(String subjectName, String subjectCode, String fileName,
                             SubjectRepository subjectRepository,
                             QuizRepository quizRepository,
                             QuestionRepository questionRepository,
                             ObjectMapper objectMapper) {
        if (subjectRepository.existsByCode(subjectCode)) {
            log.info("Subject {} already exists, skipping.", subjectName);
            return;
        }

        try {
            Subject subject = new Subject();
            subject.setName(subjectName);
            subject.setCode(subjectCode);
            subject.setDescription(subjectName + " questions for Class 10");
            subject = subjectRepository.save(subject);

            Resource resource = new ClassPathResource(fileName);
            if (!resource.exists()) {
                log.warn("Seed file {} not found in classpath", fileName);
                return;
            }

            List<Map<String, Object>> questionData;
            try (InputStream inputStream = resource.getInputStream()) {
                questionData = objectMapper.readValue(inputStream, new TypeReference<List<Map<String, Object>>>() {});
            }

            Quiz quiz = new Quiz();
            quiz.setTitle("Class 10 " + subjectName + " Comprehensive Quiz");
            quiz.setDescription("Full set of 50 questions for " + subjectName);
            quiz.setTotalMarks(questionData.size());
            quiz.setDurationMinutes(60);
            quiz.setSubject(subject);
            quiz.setIsActive(true);
            quiz = quizRepository.save(quiz);

            List<Question> questions = new ArrayList<>();
            for (Map<String, Object> data : questionData) {
                Question question = new Question();
                question.setText((String) data.get("text"));
                question.setOptions((List<String>) data.get("options"));
                question.setCorrectAnswer((String) data.get("ans"));
                
                Object marksObj = data.get("marks");
                if (marksObj instanceof Number) {
                    question.setMarks(((Number) marksObj).intValue());
                } else if (marksObj != null) {
                    question.setMarks(Integer.parseInt(marksObj.toString()));
                } else {
                    question.setMarks(1);
                }
                
                question.setQuiz(quiz);
                questions.add(question);
            }
            questionRepository.saveAll(questions);
            log.info("Successfully seeded {} questions for {}.", questions.size(), subjectName);
        } catch (Exception e) {
            log.error("Error seeding {}: {}", subjectName, e.getMessage());
        }
    }
}
