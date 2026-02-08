package com.quiz.service.impl;

import com.quiz.dto.*;
import com.quiz.model.*;
import com.quiz.repository.*;
import com.quiz.service.AdminService;
import org.springframework.stereotype.Service;
import com.quiz.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private ResultRepository resultRepository;

    @Override
    public SubjectDto createSubject(SubjectDto dto) {
        Subject subject = new Subject();
        subject.setName(dto.getName());
        subject.setCode(dto.getCode());
        subject.setDescription(dto.getDescription());
        Subject saved = subjectRepository.save(subject);
        return mapToDto(saved);
    }

    @Override
    public List<SubjectDto> getAllSubjects() {
        return subjectRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public QuizDto createQuiz(QuizDto dto) {
        Subject subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));

        Quiz quiz = new Quiz();
        quiz.setTitle(dto.getTitle());
        quiz.setDescription(dto.getDescription());
        quiz.setTotalMarks(dto.getTotalMarks());
        quiz.setDurationMinutes(dto.getDurationMinutes());
        quiz.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        quiz.setSubject(subject);

        Quiz saved = quizRepository.save(quiz);
        return mapToDto(saved);
    }

    @Override
    public List<QuizDto> getQuizzesBySubject(Long subjectId) {
        return quizRepository.findBySubjectId(subjectId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public QuestionDto addQuestion(QuestionDto dto) {
        Quiz quiz = quizRepository.findById(dto.getQuizId())
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found"));

        Question question = new Question();
        question.setText(dto.getText());
        question.setOptions(dto.getOptions());
        question.setCorrectAnswer(dto.getCorrectAnswer());
        question.setMarks(dto.getMarks());
        question.setQuiz(quiz);

        Question saved = questionRepository.save(question);
        return mapToDto(saved);
    }

    @Override
    public List<QuestionDto> getQuestionsByQuiz(Long quizId) {
        return questionRepository.findByQuizId(quizId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResultDto> getAllResults() {
        return resultRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResultDto> getResultsByQuiz(Long quizId) {
        return resultRepository.findByQuizId(quizId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Mappers
    private SubjectDto mapToDto(Subject s) {
        SubjectDto dto = new SubjectDto();
        dto.setId(s.getId());
        dto.setName(s.getName());
        dto.setCode(s.getCode());
        dto.setDescription(s.getDescription());
        return dto;
    }

    private QuizDto mapToDto(Quiz q) {
        QuizDto dto = new QuizDto();
        dto.setId(q.getId());
        dto.setTitle(q.getTitle());
        dto.setDescription(q.getDescription());
        dto.setTotalMarks(q.getTotalMarks());
        dto.setDurationMinutes(q.getDurationMinutes());
        dto.setIsActive(q.getIsActive());
        dto.setSubjectId(q.getSubject().getId());
        dto.setSubjectName(q.getSubject().getName());
        return dto;
    }

    private QuestionDto mapToDto(Question q) {
        QuestionDto dto = new QuestionDto();
        dto.setId(q.getId());
        dto.setText(q.getText());
        dto.setOptions(q.getOptions());
        dto.setCorrectAnswer(q.getCorrectAnswer());
        dto.setMarks(q.getMarks());
        dto.setQuizId(q.getQuiz().getId());
        return dto;
    }

    private ResultDto mapToDto(Result r) {
        ResultDto dto = new ResultDto();
        dto.setId(r.getId());
        dto.setStudentName(r.getStudent().getName());
        dto.setQuizTitle(r.getQuiz().getTitle());
        dto.setScore(r.getScore());
        dto.setTotalMarks(r.getTotalMarks());
        dto.setSubmittedAt(r.getSubmittedAt());
        return dto;
    }
}
