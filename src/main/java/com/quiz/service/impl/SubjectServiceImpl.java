package com.quiz.service.impl;

import com.quiz.common.Constants;
import com.quiz.dto.SubjectDto;
import com.quiz.exception.ResourceNotFoundException;
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
    public SubjectDto updateSubject(Long id, SubjectDto dto) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.ERR_SUBJECT_NOT_FOUND));
        
        subject.setName(dto.getName());
        subject.setCode(dto.getCode());
        subject.setDescription(dto.getDescription());
        
        Subject updated = subjectRepository.save(subject);
        return subjectMapper.toDto(updated);
    }

    @Override
    public void deleteSubject(Long id) {
        if (!subjectRepository.existsById(id)) {
            throw new ResourceNotFoundException(Constants.ERR_SUBJECT_NOT_FOUND);
        }
        subjectRepository.deleteById(id);
    }

    @Override
    public List<SubjectDto> getAllSubjects() {
        return subjectRepository.findAll().stream()
                .map(subjectMapper::toDto)
                .collect(Collectors.toList());
    }
}
