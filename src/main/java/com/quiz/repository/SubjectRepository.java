package com.quiz.repository;

import com.quiz.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    boolean existsByName(String name);
    boolean existsByCode(String code);
}
