package com.quiz.service.impl;

import com.quiz.model.User;
import com.quiz.dto.UserDTO;
import com.quiz.model.Student;
import com.quiz.dto.LoginRequest;
import com.quiz.common.Constants;
import com.quiz.dto.LoginResponse;
import com.quiz.service.AuthService;
import com.quiz.dto.RegisterRequest;
import com.quiz.exception.AuthException;
import com.quiz.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public LoginResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AuthException(Constants.ERR_EMAIL_EXISTS);
        }

        Student student = new Student();
        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setPassword(passwordEncoder.encode(request.getPassword()));
        student.setRole(User.Role.STUDENT);

        User savedUser = userRepository.save(student);
        
        return LoginResponse.builder()
                .user(UserDTO.builder()
                        .id(savedUser.getId())
                        .name(savedUser.getName())
                        .email(savedUser.getEmail())
                        .role(savedUser.getRole())
                        .build())
                .token(java.util.Base64.getEncoder().encodeToString(savedUser.getEmail().getBytes()))
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AuthException(Constants.ERR_USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AuthException(Constants.ERR_INVALID_CREDENTIALS);
        }

        return LoginResponse.builder()
                .user(UserDTO.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .build())
                .token(java.util.Base64.getEncoder().encodeToString(user.getEmail().getBytes()))
                .build();
    }
}
