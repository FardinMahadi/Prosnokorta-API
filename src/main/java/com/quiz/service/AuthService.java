package com.quiz.service;

import com.quiz.model.User;
import com.quiz.dto.LoginRequest;
import com.quiz.dto.RegisterRequest;

import java.util.Map;

public interface AuthService {
    User register(RegisterRequest request);
    Map<String, Object> login(LoginRequest request);
}
