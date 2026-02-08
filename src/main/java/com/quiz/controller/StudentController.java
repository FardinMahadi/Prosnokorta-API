package com.quiz.controller;

import com.quiz.dto.QuizSubmission;
import com.quiz.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = "*")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/subjects")
    public ResponseEntity<?> getAllSubjects() {
        return ResponseEntity.ok(studentService.getAllSubjects());
    }

    @GetMapping("/quizzes/{subjectId}")
    public ResponseEntity<?> getAvailableQuizzes(@PathVariable Long subjectId) {
        return ResponseEntity.ok(studentService.getAvailableQuizzes(subjectId));
    }

    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<?> startQuiz(@PathVariable Long quizId) {
        return ResponseEntity.ok(studentService.startQuiz(quizId));
    }

    @PostMapping("/quiz/{quizId}/submit")
    public ResponseEntity<?> submitQuiz(@PathVariable Long quizId, 
                                        @RequestBody QuizSubmission submission,
                                        @RequestParam Long studentId) {
        // Pass studentId as param since no JWT/Session
        return ResponseEntity.ok(studentService.submitQuiz(quizId, submission, studentId));
    }

    @GetMapping("/results")
    public ResponseEntity<?> getMyResults(@RequestParam Long studentId) {
        return ResponseEntity.ok(studentService.getMyResults(studentId));
    }

    @GetMapping("/results/{resultId}")
    public ResponseEntity<?> getResultDetails(@PathVariable Long resultId) {
        return ResponseEntity.ok(studentService.getResultDetails(resultId));
    }
}
