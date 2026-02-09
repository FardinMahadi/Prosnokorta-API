package com.quiz.controller;

import com.quiz.common.ApiResponse;
import com.quiz.common.Constants;
import com.quiz.dto.QuizDto;
import com.quiz.dto.QuizSubmission;
import com.quiz.dto.ResultDto;
import com.quiz.dto.SubjectDto;
import com.quiz.service.QuizService;
import com.quiz.service.ResultService;
import com.quiz.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class StudentController {

    private final SubjectService subjectService;
    private final QuizService quizService;
    private final ResultService resultService;

    @GetMapping("/subjects")
    public ResponseEntity<ApiResponse<List<SubjectDto>>> getAllSubjects() {
        return ResponseEntity.ok(ApiResponse.success(subjectService.getAllSubjects(), Constants.MSG_SUBJECTS_RETRIEVED));
    }

    @GetMapping("/quizzes/{subjectId}")
    public ResponseEntity<ApiResponse<List<QuizDto>>> getAvailableQuizzes(@PathVariable Long subjectId) {
        return ResponseEntity.ok(ApiResponse.success(quizService.getAvailableQuizzes(subjectId), Constants.MSG_QUIZZES_RETRIEVED));
    }

    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<ApiResponse<QuizDto>> startQuiz(@PathVariable Long quizId) {
        return ResponseEntity.ok(ApiResponse.success(quizService.startQuiz(quizId), Constants.MSG_QUIZ_STARTED));
    }

    @PostMapping("/quiz/{quizId}/submit")
    public ResponseEntity<ApiResponse<ResultDto>> submitQuiz(@PathVariable Long quizId, 
                                         @RequestBody QuizSubmission submission,
                                         @RequestParam Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(resultService.submitQuiz(quizId, submission, studentId), Constants.MSG_QUIZ_SUBMITTED));
    }

    @GetMapping("/results")
    public ResponseEntity<ApiResponse<List<ResultDto>>> getMyResults(@RequestParam Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(resultService.getMyResults(studentId), Constants.MSG_RESULTS_RETRIEVED));
    }

    @GetMapping("/results/{resultId}")
    public ResponseEntity<ApiResponse<ResultDto>> getResultDetails(@PathVariable Long resultId) {
        return ResponseEntity.ok(ApiResponse.success(resultService.getResultDetails(resultId), Constants.MSG_RESULTS_RETRIEVED));
    }
}
