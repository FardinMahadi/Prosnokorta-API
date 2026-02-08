package com.quiz.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "students")
public class Student extends User {
    // Add specific student fields if needed in future
}
