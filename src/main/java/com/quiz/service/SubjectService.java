package com.quiz.service;

import com.quiz.dto.SubjectDto;
import java.util.List;

public interface SubjectService {
    SubjectDto createSubject(SubjectDto dto);
    List<SubjectDto> getAllSubjects();
}
