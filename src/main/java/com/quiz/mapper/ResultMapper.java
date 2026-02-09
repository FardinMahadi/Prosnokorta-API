package com.quiz.mapper;

import com.quiz.dto.ResultDto;
import com.quiz.model.Result;
import org.springframework.stereotype.Component;

@Component
public class ResultMapper {

    public ResultDto toDto(Result result) {
        if (result == null) return null;
        ResultDto dto = new ResultDto();
        dto.setId(result.getId());
        if (result.getStudent() != null) {
            dto.setStudentName(result.getStudent().getName());
        }
        if (result.getQuiz() != null) {
            dto.setQuizTitle(result.getQuiz().getTitle());
        }
        dto.setScore(result.getScore());
        dto.setTotalMarks(result.getTotalMarks());
        dto.setSubmittedAt(result.getSubmittedAt());
        return dto;
    }
}
