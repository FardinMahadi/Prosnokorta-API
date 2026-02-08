package com.quiz.dto;

import lombok.Data;

@Data
public class QuizDto {
    private Long id;
    private String title;
    private String description;
    private Integer totalMarks;
    private Integer durationMinutes;
    private Boolean isActive;
    private Long subjectId; // For creation/linking
    private String subjectName; // For display
    private java.util.List<QuestionDto> questions; // For Start Quiz response
}
