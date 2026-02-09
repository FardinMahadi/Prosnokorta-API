package com.quiz.mapper;

import com.quiz.dto.QuizDto;
import com.quiz.model.Quiz;
import org.springframework.stereotype.Component;

@Component
public class QuizMapper {

    public QuizDto toDto(Quiz quiz) {
        if (quiz == null) return null;
        QuizDto dto = new QuizDto();
        dto.setId(quiz.getId());
        dto.setTitle(quiz.getTitle());
        dto.setDescription(quiz.getDescription());
        dto.setTotalMarks(quiz.getTotalMarks());
        dto.setDurationMinutes(quiz.getDurationMinutes());
        dto.setIsActive(quiz.getIsActive());
        if (quiz.getSubject() != null) {
            dto.setSubjectId(quiz.getSubject().getId());
            dto.setSubjectName(quiz.getSubject().getName());
        }
        return dto;
    }

    public Quiz toEntity(QuizDto dto) {
        if (dto == null) return null;
        Quiz quiz = new Quiz();
        quiz.setTitle(dto.getTitle());
        quiz.setDescription(dto.getDescription());
        quiz.setTotalMarks(dto.getTotalMarks());
        quiz.setDurationMinutes(dto.getDurationMinutes());
        quiz.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        return quiz;
    }
}
