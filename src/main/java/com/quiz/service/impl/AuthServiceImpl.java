package com.quiz.service.impl;

import com.quiz.common.Constants;
import com.quiz.model.User;
import com.quiz.model.Student;
import com.quiz.dto.LoginRequest;
import com.quiz.dto.LoginResponse;
import com.quiz.service.AuthService;
import com.quiz.dto.RegisterRequest;
import com.quiz.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException(Constants.ERR_EMAIL_EXISTS);
        }

        Student student = new Student();
        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setPassword(request.getPassword()); // Should encode in real app
        student.setRole(User.Role.STUDENT);

        return userRepository.save(student);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException(Constants.ERR_USER_NOT_FOUND));

        if (!user.getPassword().equals(request.getPassword())) { // Simple check
            throw new RuntimeException(Constants.ERR_INVALID_CREDENTIALS);
        }

        return LoginResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
