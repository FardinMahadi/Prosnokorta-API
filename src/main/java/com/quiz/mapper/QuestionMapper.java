package com.quiz.mapper;

import com.quiz.dto.QuestionDto;
import com.quiz.model.Question;
import org.springframework.stereotype.Component;

@Component
public class QuestionMapper {

    public QuestionDto toDto(Question question) {
        if (question == null) return null;
        QuestionDto dto = new QuestionDto();
        dto.setId(question.getId());
        dto.setText(question.getText());
        dto.setOptions(question.getOptions());
        dto.setCorrectAnswer(question.getCorrectAnswer());
        dto.setMarks(question.getMarks());
        if (question.getQuiz() != null) {
            dto.setQuizId(question.getQuiz().getId());
        }
        return dto;
    }

    public Question toEntity(QuestionDto dto) {
        if (dto == null) return null;
        Question question = new Question();
        question.setText(dto.getText());
        question.setOptions(dto.getOptions());
        question.setCorrectAnswer(dto.getCorrectAnswer());
        question.setMarks(dto.getMarks());
        return question;
    }
}
