package com.quiz.service;

import com.quiz.dto.QuestionDto;
import java.util.List;

public interface QuestionService {
    QuestionDto addQuestion(QuestionDto dto);
    List<QuestionDto> getQuestionsByQuiz(Long quizId);
}
