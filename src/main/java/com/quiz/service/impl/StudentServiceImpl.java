package com.quiz.service.impl;

import com.quiz.dto.*;
import com.quiz.model.*;
import com.quiz.repository.*;
import com.quiz.service.StudentService;
import org.springframework.stereotype.Service;
import com.quiz.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private ResultRepository resultRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<SubjectDto> getAllSubjects() {
        return subjectRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<QuizDto> getAvailableQuizzes(Long subjectId) {
        return quizRepository.findBySubjectIdAndIsActiveTrue(subjectId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public QuizDto startQuiz(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found"));
        
        return mapToDtoWithQuestions(quiz);
    }

    @Override
    public ResultDto submitQuiz(Long quizId, QuizSubmission submission, Long studentId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found"));
        Student student = (Student) userRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        List<Question> questions = quiz.getQuestions();
        
        // Better score calculation:
        int calculatedScore = 0;
        for (QuizSubmission.Answer answer : submission.getAnswers()) {
             Question q = questions.stream()
                 .filter(que -> que.getId().equals(answer.getQuestionId()))
                 .findFirst()
                 .orElse(null);
             if (q != null && q.getCorrectAnswer().equals(answer.getSelectedAnswer())) {
                 calculatedScore += q.getMarks();
             }
        }

        Result result = new Result();
        result.setStudent(student);
        result.setQuiz(quiz);
        result.setScore(calculatedScore);
        result.setTotalMarks(quiz.getTotalMarks());
        
        Result saved = resultRepository.save(result);
        return mapToDto(saved);
    }

    @Override
    public List<ResultDto> getMyResults(Long studentId) {
        return resultRepository.findByStudentId(studentId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ResultDto getResultDetails(Long resultId) {
        Result result = resultRepository.findById(resultId)
                .orElseThrow(() -> new ResourceNotFoundException("Result not found"));
        return mapToDto(result);
    }

    // Mappers
    private SubjectDto mapToDto(Subject s) {
        SubjectDto dto = new SubjectDto();
        dto.setId(s.getId());
        dto.setName(s.getName());
        dto.setCode(s.getCode());
        dto.setDescription(s.getDescription());
        return dto;
    }

    private QuizDto mapToDto(Quiz q) {
        QuizDto dto = new QuizDto();
        dto.setId(q.getId());
        dto.setTitle(q.getTitle());
        dto.setDescription(q.getDescription());
        dto.setTotalMarks(q.getTotalMarks());
        dto.setDurationMinutes(q.getDurationMinutes());
        dto.setIsActive(q.getIsActive());
        dto.setSubjectId(q.getSubject().getId());
        dto.setSubjectName(q.getSubject().getName());
        // I'll fetch questions and map them here if needed, but standard mapping might not include heavy list.
        // For startQuiz I need it.
        return dto;
    }
    
    // helper for startQuiz
    public QuizDto mapToDtoWithQuestions(Quiz q) {
        QuizDto dto = mapToDto(q);
        List<QuestionDto> qDtos = q.getQuestions().stream().map(question -> {
            QuestionDto qDto = new QuestionDto();
            qDto.setId(question.getId());
            qDto.setText(question.getText());
            qDto.setOptions(question.getOptions());
            qDto.setMarks(question.getMarks());
            qDto.setQuizId(q.getId());
            qDto.setCorrectAnswer(null); // Explicitly hide
            return qDto;
        }).collect(Collectors.toList());
        dto.setQuestions(qDtos);
        return dto;
    }

    private ResultDto mapToDto(Result r) {
        ResultDto dto = new ResultDto();
        dto.setId(r.getId());
        dto.setStudentName(r.getStudent().getName());
        dto.setQuizTitle(r.getQuiz().getTitle());
        dto.setScore(r.getScore());
        dto.setTotalMarks(r.getTotalMarks());
        dto.setSubmittedAt(r.getSubmittedAt());
        return dto;
    }
}
