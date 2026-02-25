package com.quiz.mapper;

import com.quiz.dto.ResultDto;
import com.quiz.model.Result;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class ResultMapper {

    public ResultDto toDto(Result result) {
        if (result == null) return null;
        
        return ResultDto.builder()
                .id(result.getId())
                .studentName(result.getStudent() != null ? result.getStudent().getName() : null)
                .quizTitle(result.getQuiz() != null ? result.getQuiz().getTitle() : null)
                .score(result.getScore())
                .totalMarks(result.getTotalMarks())
                .submittedAt(result.getSubmittedAt())
                .analysis(result.getAnswers() != null ? result.getAnswers().stream()
                        .map(answer -> ResultDto.QuestionAnalysis.builder()
                                .questionText(answer.getQuestionText())
                                .selectedAnswer(answer.getSelectedAnswer())
                                .correctAnswer(answer.getCorrectAnswer())
                                .isCorrect(answer.isCorrect())
                                .marksAwarded(answer.getMarksAwarded())
                                .build())
                        .collect(Collectors.toList()) : null)
                .build();
    }
}
