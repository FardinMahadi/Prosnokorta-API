package com.quiz.service;

import com.quiz.dto.QuestionDto;
import java.util.List;

public interface QuestionService {
    QuestionDto addQuestion(QuestionDto dto);
    void deleteQuestion(Long id);
    List<QuestionDto> getQuestionsByQuiz(Long quizId);
}
