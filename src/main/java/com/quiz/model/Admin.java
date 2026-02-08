package com.quiz.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "admins")
public class Admin extends User {
    // Add specific admin fields if needed in future
}
