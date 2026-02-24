package com.quiz.controller;

import com.quiz.common.ApiResponse;
import com.quiz.common.Constants;
import com.quiz.dto.*;
import com.quiz.service.QuestionService;
import com.quiz.service.QuizService;
import com.quiz.service.ResultService;
import com.quiz.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@CrossOrigin(origins = "*") // Allow all for dev
@RequiredArgsConstructor
public class AdminController {

    private final SubjectService subjectService;
    private final QuizService quizService;
    private final QuestionService questionService;
    private final ResultService resultService;

    // Subjects
    @PostMapping("/subjects")
    public ResponseEntity<ApiResponse<SubjectDto>> createSubject(@RequestBody SubjectDto dto) {
        return new ResponseEntity<>(
            ApiResponse.success(subjectService.createSubject(dto), Constants.MSG_SUBJECT_CREATED),
            HttpStatus.CREATED
        );
    }

    @GetMapping("/subjects")
    public ResponseEntity<ApiResponse<List<SubjectDto>>> getAllSubjects() {
        return ResponseEntity.ok(ApiResponse.success(subjectService.getAllSubjects(), Constants.MSG_SUBJECTS_RETRIEVED));
    }

    @PutMapping("/subjects/{id}")
    public ResponseEntity<ApiResponse<SubjectDto>> updateSubject(@PathVariable Long id, @RequestBody SubjectDto dto) {
        return ResponseEntity.ok(ApiResponse.success(subjectService.updateSubject(id, dto), Constants.MSG_SUBJECT_UPDATED));
    }

    @DeleteMapping("/subjects/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.ok(ApiResponse.success(null, Constants.MSG_SUBJECT_DELETED));
    }

    // Quizzes
    @PostMapping("/quizzes")
    public ResponseEntity<ApiResponse<QuizDto>> createQuiz(@RequestBody QuizDto dto) {
        return new ResponseEntity<>(
            ApiResponse.success(quizService.createQuiz(dto), Constants.MSG_QUIZ_CREATED),
            HttpStatus.CREATED
        );
    }

    @GetMapping("/quizzes/{subjectId}")
    public ResponseEntity<ApiResponse<List<QuizDto>>> getQuizzesBySubject(@PathVariable Long subjectId) {
        return ResponseEntity.ok(ApiResponse.success(quizService.getQuizzesBySubject(subjectId), Constants.MSG_QUIZZES_RETRIEVED));
    }

    @PutMapping("/quizzes/{id}")
    public ResponseEntity<ApiResponse<QuizDto>> updateQuiz(@PathVariable Long id, @RequestBody QuizDto dto) {
        return ResponseEntity.ok(ApiResponse.success(quizService.updateQuiz(id, dto), Constants.MSG_QUIZ_UPDATED));
    }

    @DeleteMapping("/quizzes/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
        return ResponseEntity.ok(ApiResponse.success(null, Constants.MSG_QUIZ_DELETED));
    }

    // Questions
    @PostMapping("/quizzes/{quizId}/questions")
    public ResponseEntity<ApiResponse<QuestionDto>> addQuestion(@PathVariable Long quizId, @RequestBody QuestionDto dto) {
        dto.setQuizId(quizId);
        return new ResponseEntity<>(
            ApiResponse.success(questionService.addQuestion(dto), Constants.MSG_QUESTION_ADDED),
            HttpStatus.CREATED
        );
    }

    @GetMapping("/quizzes/{quizId}/questions")
    public ResponseEntity<ApiResponse<List<QuestionDto>>> getQuestionsByQuiz(@PathVariable Long quizId) {
        return ResponseEntity.ok(ApiResponse.success(questionService.getQuestionsByQuiz(quizId), Constants.MSG_QUESTIONS_RETRIEVED));
    }

    @DeleteMapping("/quizzes/{quizId}/questions/{questionId}")
    public ResponseEntity<ApiResponse<Void>> deleteQuestion(@PathVariable Long quizId, @PathVariable Long questionId) {
        // quizId is present for URL consistency, but we only need questionId for now
        questionService.deleteQuestion(questionId);
        return ResponseEntity.ok(ApiResponse.success(null, Constants.MSG_QUESTION_DELETED));
    }

    // Results
    @GetMapping("/results")
    public ResponseEntity<ApiResponse<List<ResultDto>>> getAllResults() {
        return ResponseEntity.ok(ApiResponse.success(resultService.getAllResults(), Constants.MSG_RESULTS_RETRIEVED));
    }

    @GetMapping("/results/quiz/{quizId}")
    public ResponseEntity<ApiResponse<List<ResultDto>>> getResultsByQuiz(@PathVariable Long quizId) {
        return ResponseEntity.ok(ApiResponse.success(resultService.getResultsByQuiz(quizId), Constants.MSG_RESULTS_RETRIEVED));
    }
}
