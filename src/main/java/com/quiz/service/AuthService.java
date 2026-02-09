package com.quiz.service;

import com.quiz.model.User;
import com.quiz.dto.LoginRequest;
import com.quiz.dto.RegisterRequest;
import com.quiz.dto.LoginResponse;

public interface AuthService {
    User register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
}
