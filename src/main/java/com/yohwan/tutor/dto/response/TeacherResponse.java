package com.yohwan.tutor.dto.response;

import com.yohwan.tutor.domain.entity.Teacher;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TeacherResponse {
    private Long id;
    private String name;
    private String memo;

    public TeacherResponse(Teacher teacher) {
        this(teacher.getId(), teacher.getName(), teacher.getMemo());
    }

    public TeacherResponse(Long id, String name, String memo) {
        this.id = id;
        this.name = name;
        this.memo = memo;
    }
}
