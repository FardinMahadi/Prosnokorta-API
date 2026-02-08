package com.quiz.repository;

import java.util.List;
import com.quiz.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findBySubjectId(Long subjectId);
    List<Quiz> findBySubjectIdAndIsActiveTrue(Long subjectId);
}
