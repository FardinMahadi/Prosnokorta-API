package com.quiz.service.impl;

import com.quiz.common.Constants;
import com.quiz.dto.QuizSubmission;
import com.quiz.dto.ResultDto;
import com.quiz.exception.ResourceNotFoundException;
import com.quiz.mapper.ResultMapper;
import com.quiz.model.*;
import com.quiz.repository.QuizRepository;
import com.quiz.repository.ResultRepository;
import com.quiz.repository.UserRepository;
import com.quiz.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final ResultMapper resultMapper;

    @Override
    public ResultDto submitQuiz(Long quizId, QuizSubmission submission, Long studentId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.ERR_QUIZ_NOT_FOUND));
        Student student = (Student) userRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.ERR_STUDENT_NOT_FOUND));

        List<Question> questions = quiz.getQuestions();
        
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
        return resultMapper.toDto(saved);
    }

    @Override
    public List<ResultDto> getAllResults() {
        return resultRepository.findAll().stream()
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
