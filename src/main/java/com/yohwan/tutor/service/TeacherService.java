package com.yohwan.tutor.service;

import com.yohwan.tutor.domain.entity.Teacher;
import com.yohwan.tutor.dto.request.TeacherUpdateRequest;
import com.yohwan.tutor.dto.response.TeacherResponse;
import com.yohwan.tutor.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;

    @Transactional(readOnly = true)
    public TeacherResponse getMyInfo(Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> {
            throw new IllegalArgumentException("존재하지 않은 아이디입니다.");
        });

        return new TeacherResponse(teacher);
    }

    public void updateMyInfo(TeacherUpdateRequest request, Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> {
            throw new IllegalArgumentException("존재하지 않은 아이디입니다.");
        });

        teacher.update(request.getName(), request.getMemo());
    }
}
