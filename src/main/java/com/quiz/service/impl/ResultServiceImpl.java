package com.quiz.service.impl;

import com.quiz.common.Constants;
import com.quiz.dto.QuizSubmission;
import com.quiz.dto.ResultDto;
import com.quiz.exception.ResourceNotFoundException;
import com.quiz.mapper.ResultMapper;
import com.quiz.model.*;
import com.quiz.repository.QuizRepository;
import com.quiz.repository.ResultRepository;
import com.quiz.repository.StudentRepository;
import com.quiz.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;
    private final QuizRepository quizRepository;
    private final StudentRepository studentRepository;
    private final ResultMapper resultMapper;

    @Override
    public ResultDto submitQuiz(Long quizId, QuizSubmission submission, Long studentId) {
        if (submission == null || submission.getAnswers() == null || submission.getAnswers().isEmpty()) {
            throw new IllegalArgumentException("Submission must contain answers");
        }

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.ERR_QUIZ_NOT_FOUND));
        
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.ERR_STUDENT_NOT_FOUND));

        List<Question> questions = quiz.getQuestions();
        
        int calculatedScore = 0;
        Set<Long> processedQuestionIds = new HashSet<>();
        List<ResultAnswer> resultAnswers = new ArrayList<>();

        for (QuizSubmission.Answer answer : submission.getAnswers()) {
             if (processedQuestionIds.contains(answer.getQuestionId())) {
                 continue;
             }

             Question q = questions.stream()
                 .filter(que -> que.getId().equals(answer.getQuestionId()))
                 .findFirst()
                 .orElse(null);
             
             if (q != null) {
                 boolean isCorrect = q.getCorrectAnswer().equals(answer.getSelectedAnswer());
                 int marks = isCorrect ? q.getMarks() : 0;
                 if (isCorrect) {
                     calculatedScore += marks;
                 }
                 
                 ResultAnswer resultAnswer = new ResultAnswer();
                 resultAnswer.setQuestionId(q.getId());
                 resultAnswer.setQuestionText(q.getText());
                 resultAnswer.setSelectedAnswer(answer.getSelectedAnswer());
                 resultAnswer.setCorrectAnswer(q.getCorrectAnswer());
                 resultAnswer.setCorrect(isCorrect);
                 resultAnswer.setMarksAwarded(marks);
                 resultAnswers.add(resultAnswer);
                 
                 processedQuestionIds.add(answer.getQuestionId());
             }
        }

        Result result = new Result();
        result.setStudent(student);
        result.setQuiz(quiz);
        result.setScore(calculatedScore);
        result.setTotalMarks(quiz.getTotalMarks());
        
        // Ensure bidirectional relationship
        for (ResultAnswer ra : resultAnswers) {
            ra.setResult(result);
        }
        result.setAnswers(resultAnswers);
        
        Result saved = resultRepository.save(result);
        return resultMapper.toDto(saved);
    }

    @Override
    public List<ResultDto> getAllResults() {
        return resultRepository.findAllWithDetails().stream()
                .map(resultMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResultDto> getResultsByQuiz(Long quizId) {
        return resultRepository.findByQuizId(quizId).stream()
                .map(resultMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResultDto> getMyResults(Long studentId) {
        return resultRepository.findByStudentId(studentId).stream()
                .map(resultMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ResultDto getResultDetails(Long resultId) {
        Result result = resultRepository.findById(resultId)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.ERR_RESULT_NOT_FOUND));
        return resultMapper.toDto(result);
    }
}
