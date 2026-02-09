package com.quiz.service.impl;

import com.quiz.common.Constants;
import com.quiz.dto.QuestionDto;
import com.quiz.dto.QuizDto;
import com.quiz.exception.ResourceNotFoundException;
import com.quiz.mapper.QuestionMapper;
import com.quiz.mapper.QuizMapper;
import com.quiz.model.Quiz;
import com.quiz.model.Subject;
import com.quiz.repository.QuizRepository;
import com.quiz.repository.SubjectRepository;
import com.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final SubjectRepository subjectRepository;
    private final QuizMapper quizMapper;
    private final QuestionMapper questionMapper;

    @Override
    public QuizDto createQuiz(QuizDto dto) {
        Subject subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException(Constants.ERR_SUBJECT_NOT_FOUND));

        Quiz quiz = quizMapper.toEntity(dto);
        quiz.setSubject(subject);

        Quiz saved = quizRepository.save(quiz);
        return quizMapper.toDto(saved);
    }

    @Override
    public List<QuizDto> getQuizzesBySubject(Long subjectId) {
        return quizRepository.findBySubjectId(subjectId).stream()
                .map(quizMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<QuizDto> getAvailableQuizzes(Long subjectId) {
        return quizRepository.findBySubjectIdAndIsActiveTrue(subjectId).stream()
                .map(quizMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public QuizDto startQuiz(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.ERR_QUIZ_NOT_FOUND));
        
        return mapToDtoWithQuestions(quiz);
    }

    private QuizDto mapToDtoWithQuestions(Quiz q) {
        QuizDto dto = quizMapper.toDto(q);
        List<QuestionDto> qDtos = q.getQuestions().stream().map(question -> {
            QuestionDto qDto = questionMapper.toDto(question);
            qDto.setCorrectAnswer(null); // Explicitly hide
            return qDto;
        }).collect(Collectors.toList());
        dto.setQuestions(qDtos);
        return dto;
    }
}
