package com.quiz.repository;

import java.util.List;
import com.quiz.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository<Result, Long> {
    List<Result> findByStudentId(Long studentId);
    List<Result> findByQuizId(Long quizId);
}
