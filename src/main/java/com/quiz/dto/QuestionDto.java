package com.quiz.dto;

import lombok.Data;
import java.util.List;

@Data
public class QuestionDto {
    private Long id;
    private String text;
    private List<String> options;
    private String correctAnswer; // Include only for Admin or nullify for Student
    private Integer marks;
    private Long quizId;
}
