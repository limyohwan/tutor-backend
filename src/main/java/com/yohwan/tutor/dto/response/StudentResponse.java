package com.yohwan.tutor.dto.response;

import com.yohwan.tutor.domain.entity.Student;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudentResponse {
    private Long id;
    private String name;

    public StudentResponse(Student student) {
        this(student.getId(), student.getName());
    }
    public StudentResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
