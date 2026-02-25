package com.quiz.repository;

import java.util.List;
import com.quiz.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ResultRepository extends JpaRepository<Result, Long> {
    
    @Query("SELECT r FROM Result r JOIN FETCH r.student JOIN FETCH r.quiz WHERE r.student.id = :studentId")
    List<Result> findByStudentId(@Param("studentId") Long studentId);
    
    @Query("SELECT r FROM Result r JOIN FETCH r.student JOIN FETCH r.quiz WHERE r.quiz.id = :quizId")
    List<Result> findByQuizId(@Param("quizId") Long quizId);

    @Query("SELECT r FROM Result r JOIN FETCH r.student JOIN FETCH r.quiz")
    List<Result> findAllWithDetails();
}
