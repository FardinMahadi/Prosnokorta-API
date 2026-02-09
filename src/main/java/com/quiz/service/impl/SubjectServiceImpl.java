package com.quiz.service.impl;

import com.quiz.dto.SubjectDto;
import com.quiz.mapper.SubjectMapper;
import com.quiz.model.Subject;
import com.quiz.repository.SubjectRepository;
import com.quiz.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    @Override
    public SubjectDto createSubject(SubjectDto dto) {
        Subject subject = subjectMapper.toEntity(dto);
        Subject saved = subjectRepository.save(subject);
        return subjectMapper.toDto(saved);
    }

    @Override
    public List<SubjectDto> getAllSubjects() {
        return subjectRepository.findAll().stream()
                .map(subjectMapper::toDto)
                .collect(Collectors.toList());
    }
}
