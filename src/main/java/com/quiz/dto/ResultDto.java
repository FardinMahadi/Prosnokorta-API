package com.quiz.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ResultDto {
    private Long id;
    private String studentName;
    private String quizTitle;
    private Integer score;
    private Integer totalMarks;
    private LocalDateTime submittedAt;
}
