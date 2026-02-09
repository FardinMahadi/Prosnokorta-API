package com.quiz.mapper;

import com.quiz.dto.SubjectDto;
import com.quiz.model.Subject;
import org.springframework.stereotype.Component;

@Component
public class SubjectMapper {

    public SubjectDto toDto(Subject subject) {
        if (subject == null) return null;
        SubjectDto dto = new SubjectDto();
        dto.setId(subject.getId());
        dto.setName(subject.getName());
        dto.setCode(subject.getCode());
        dto.setDescription(subject.getDescription());
        return dto;
    }

    public Subject toEntity(SubjectDto dto) {
        if (dto == null) return null;
        Subject subject = new Subject();
        subject.setName(dto.getName());
        subject.setCode(dto.getCode());
        subject.setDescription(dto.getDescription());
        return subject;
    }
}
