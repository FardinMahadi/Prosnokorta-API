package com.quiz.service.impl;

import com.quiz.common.Constants;
import com.quiz.dto.QuestionDto;
import com.quiz.exception.ResourceNotFoundException;
import com.quiz.mapper.QuestionMapper;
import com.quiz.model.Question;
import com.quiz.model.Quiz;
import com.quiz.repository.QuestionRepository;
import com.quiz.repository.QuizRepository;
import com.quiz.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;
    private final QuestionMapper questionMapper;

    @Override
    public QuestionDto addQuestion(QuestionDto dto) {
        Quiz quiz = quizRepository.findById(dto.getQuizId())
                .orElseThrow(() -> new ResourceNotFoundException(Constants.ERR_QUIZ_NOT_FOUND));

        Question question = questionMapper.toEntity(dto);
        question.setQuiz(quiz);

        Question saved = questionRepository.save(question);
        return questionMapper.toDto(saved);
    }

    @Override
    public List<QuestionDto> getQuestionsByQuiz(Long quizId) {
        return questionRepository.findByQuizId(quizId).stream()
                .map(questionMapper::toDto)
                .collect(Collectors.toList());
    }
}
