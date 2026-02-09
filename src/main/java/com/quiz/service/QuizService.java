package com.quiz.service;

import com.quiz.dto.QuizDto;
import java.util.List;

public interface QuizService {
    QuizDto createQuiz(QuizDto dto);
    List<QuizDto> getQuizzesBySubject(Long subjectId);
    List<QuizDto> getAvailableQuizzes(Long subjectId);
    QuizDto startQuiz(Long quizId);
}
