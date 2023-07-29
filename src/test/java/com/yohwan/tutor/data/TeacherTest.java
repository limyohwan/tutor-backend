package com.yohwan.tutor.data;

import com.yohwan.tutor.domain.entity.Teacher;
import com.yohwan.tutor.dto.request.TeacherUpdateRequest;
import com.yohwan.tutor.dto.response.TeacherResponse;

public class TeacherTest {
    protected Teacher teacher() {
        return Teacher.builder().name("임요환").memo("열혈선생님").build();
    }

    protected TeacherUpdateRequest updateRequest() {
        return new TeacherUpdateRequest("임요환2", "열혈선생님2");
    }

    protected TeacherResponse teacherResponse(long teacherId) {
        return new TeacherResponse(teacherId, "임요환", "열혈선생님");
    }
}
