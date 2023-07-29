package com.yohwan.tutor.data;

import com.yohwan.tutor.domain.entity.Student;
import com.yohwan.tutor.dto.request.StudentSaveRequest;
import com.yohwan.tutor.dto.request.StudentUpdateRequest;
import com.yohwan.tutor.dto.response.StudentResponse;

import java.util.ArrayList;
import java.util.List;

public class StudentTest {
    protected Student student() {
        return Student.builder().name("임요환").build();
    }

    protected List<Student> studentList() {
        List<Student> result = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            result.add(Student.builder().name("임요환" + i).build());
        }
        return result;
    }

    protected StudentSaveRequest saveRequest() {
        return new StudentSaveRequest("임요환");
    }

    protected StudentUpdateRequest updateRequest() {
        return new StudentUpdateRequest("임요환2");
    }

    protected StudentResponse studentResponse() {
        return new StudentResponse(1L, "임요환");
    }
}
