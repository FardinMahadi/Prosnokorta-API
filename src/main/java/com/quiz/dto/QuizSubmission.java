package com.quiz.dto;

import lombok.Data;
import java.util.List;

@Data
public class QuizSubmission {
    private Long quizId;
    private List<Answer> answers;

    @Data
    public static class Answer {
        private Long questionId;
        private String selectedAnswer;
    }
}
