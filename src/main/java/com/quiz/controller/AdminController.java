package com.quiz.controller;

import com.quiz.dto.*;
import com.quiz.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*") // Allow all for dev
public class AdminController {

    @Autowired
    private AdminService adminService;

    // Subjects
    @PostMapping("/subjects")
    public ResponseEntity<SubjectDto> createSubject(@RequestBody SubjectDto dto) {
        return new ResponseEntity<>(adminService.createSubject(dto), HttpStatus.CREATED);
    }

    @GetMapping("/subjects")
    public ResponseEntity<?> getAllSubjects() {
        return ResponseEntity.ok(adminService.getAllSubjects());
    }

    // Quizzes
    @PostMapping("/quizzes")
    public ResponseEntity<QuizDto> createQuiz(@RequestBody QuizDto dto) {
        return new ResponseEntity<>(adminService.createQuiz(dto), HttpStatus.CREATED);
    }

    @GetMapping("/quizzes/{subjectId}")
    public ResponseEntity<?> getQuizzesBySubject(@PathVariable Long subjectId) {
        return ResponseEntity.ok(adminService.getQuizzesBySubject(subjectId));
    }

    // Questions
    @PostMapping("/quizzes/{quizId}/questions")
    public ResponseEntity<QuestionDto> addQuestion(@PathVariable Long quizId, @RequestBody QuestionDto dto) {
        // Ensure quizId in param matches dto or set it
        dto.setQuizId(quizId);
        return new ResponseEntity<>(adminService.addQuestion(dto), HttpStatus.CREATED);
    }

    @GetMapping("/quizzes/{quizId}/questions")
    public ResponseEntity<?> getQuestionsByQuiz(@PathVariable Long quizId) {
        return ResponseEntity.ok(adminService.getQuestionsByQuiz(quizId));
    }

    // Results
    @GetMapping("/results")
    public ResponseEntity<?> getAllResults() {
        return ResponseEntity.ok(adminService.getAllResults());
    }

    @GetMapping("/results/quiz/{quizId}")
    public ResponseEntity<?> getResultsByQuiz(@PathVariable Long quizId) {
        return ResponseEntity.ok(adminService.getResultsByQuiz(quizId));
    }
}
