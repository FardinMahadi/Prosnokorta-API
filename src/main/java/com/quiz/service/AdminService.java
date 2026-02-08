package com.quiz.service;

import com.quiz.dto.*;
import java.util.List;

public interface AdminService {
    SubjectDto createSubject(SubjectDto subjectDto);
    List<SubjectDto> getAllSubjects();
    QuizDto createQuiz(QuizDto quizDto);
    List<QuizDto> getQuizzesBySubject(Long subjectId);
    QuestionDto addQuestion(QuestionDto questionDto);
    List<QuestionDto> getQuestionsByQuiz(Long quizId);
    List<ResultDto> getAllResults();
    List<ResultDto> getResultsByQuiz(Long quizId);
}
