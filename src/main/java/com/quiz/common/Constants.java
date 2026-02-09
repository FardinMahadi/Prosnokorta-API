package com.quiz.common;

public class Constants {
    // Roles
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_STUDENT = "STUDENT";

    // Messages
    public static final String MSG_REGISTER_SUCCESS = "User registered successfully";
    public static final String MSG_LOGIN_SUCCESS = "Login successful";
    public static final String MSG_LOGOUT_SUCCESS = "Logged out successfully";
    public static final String MSG_SUBJECT_CREATED = "Subject created successfully";
    public static final String MSG_SUBJECTS_RETRIEVED = "Subjects retrieved successfully";
    public static final String MSG_QUIZ_CREATED = "Quiz created successfully";
    public static final String MSG_QUIZZES_RETRIEVED = "Quizzes retrieved successfully";
    public static final String MSG_QUESTION_ADDED = "Question added successfully";
    public static final String MSG_QUESTIONS_RETRIEVED = "Questions retrieved successfully";
    public static final String MSG_RESULTS_RETRIEVED = "Results retrieved successfully";
    public static final String MSG_QUIZ_SUBMITTED = "Quiz submitted successfully";
    public static final String MSG_QUIZ_STARTED = "Quiz started successfully";

    // Error Messages
    public static final String ERR_EMAIL_EXISTS = "Email already active";
    public static final String ERR_USER_NOT_FOUND = "User not found";
    public static final String ERR_INVALID_CREDENTIALS = "Invalid credentials";
    public static final String ERR_SUBJECT_NOT_FOUND = "Subject not found";
    public static final String ERR_QUIZ_NOT_FOUND = "Quiz not found";
    public static final String ERR_RESULT_NOT_FOUND = "Result not found";
    public static final String ERR_STUDENT_NOT_FOUND = "Student not found";

    private Constants() {} // Prevent instantiation
}
