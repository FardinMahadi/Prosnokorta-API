package com.quiz.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultDto {
    private Long id;
    private String studentName;
    private String quizTitle;
    private Integer score;
    private Integer totalMarks;
    private LocalDateTime submittedAt;
    private List<QuestionAnalysis> analysis;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionAnalysis {
        private String questionText;
        private String selectedAnswer;
        private String correctAnswer;
        private boolean isCorrect;
        private Integer marksAwarded;
    }
}
