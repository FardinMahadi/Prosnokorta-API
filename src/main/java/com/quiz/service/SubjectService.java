package com.quiz.service;

import com.quiz.dto.SubjectDto;
import java.util.List;

public interface SubjectService {
    SubjectDto createSubject(SubjectDto dto);
    SubjectDto updateSubject(Long id, SubjectDto dto);
    void deleteSubject(Long id);
    List<SubjectDto> getAllSubjects();
}
