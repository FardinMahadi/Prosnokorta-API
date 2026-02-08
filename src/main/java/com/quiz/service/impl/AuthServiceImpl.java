package com.quiz.service.impl;

import com.quiz.model.User;
import com.quiz.model.Student;
import com.quiz.dto.LoginRequest;
import com.quiz.service.AuthService;
import com.quiz.dto.RegisterRequest;
import com.quiz.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.password.PasswordEncoder;
// Use simple comparison if security not fully set up yet, or add BCrypt later.
// For now, simple string matching (NOT SECURE but follows "Basic Level" prompt).
// Will update to BCrypt in SecurityConfig step.

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    // @Autowired
    // private PasswordEncoder passwordEncoder;

    @Override
    public User register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already active");
        }

        Student student = new Student();
        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setPassword(request.getPassword()); // Should encode in real app
        student.setRole(User.Role.STUDENT);

        return userRepository.save(student);
    }

    @Override
    public Map<String, Object> login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(request.getPassword())) { // Simple check
            throw new RuntimeException("Invalid credentials");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("name", user.getName());
        response.put("email", user.getEmail());
        response.put("role", user.getRole());
        return response;
    }
}
