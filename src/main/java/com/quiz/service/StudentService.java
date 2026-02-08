package com.quiz.service;

import com.quiz.dto.*;
import java.util.List;

public interface StudentService {
    List<SubjectDto> getAllSubjects();
    List<QuizDto> getAvailableQuizzes(Long subjectId);
    QuizDto startQuiz(Long quizId);
    ResultDto submitQuiz(Long quizId, QuizSubmission submission, Long studentId);
    List<ResultDto> getMyResults(Long studentId);
    ResultDto getResultDetails(Long resultId);
}
