package com.quiz.service;

import com.quiz.dto.QuizSubmission;
import com.quiz.dto.ResultDto;
import java.util.List;

public interface ResultService {
    ResultDto submitQuiz(Long quizId, QuizSubmission submission, Long studentId);
    List<ResultDto> getAllResults();
    List<ResultDto> getResultsByQuiz(Long quizId);
    List<ResultDto> getMyResults(Long studentId);
    ResultDto getResultDetails(Long resultId);
}
