package com.quiz.controller;

import com.quiz.common.ApiResponse;
import com.quiz.common.Constants;
import com.quiz.dto.LoginRequest;
import com.quiz.dto.LoginResponse;
import com.quiz.dto.RegisterRequest;
import com.quiz.model.User;
import com.quiz.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*") // Allow all for dev
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> register(@RequestBody RegisterRequest request) {
        User user = authService.register(request);
        return new ResponseEntity<>(
            ApiResponse.success(user, Constants.MSG_REGISTER_SUCCESS),
            HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success(response, Constants.MSG_LOGIN_SUCCESS));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout() {
        return ResponseEntity.ok(ApiResponse.success(null, Constants.MSG_LOGOUT_SUCCESS));
    }
}
