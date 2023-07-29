package com.yohwan.tutor.service;

import com.yohwan.tutor.annotation.ServiceTest;
import com.yohwan.tutor.domain.entity.Teacher;
import com.yohwan.tutor.data.TeacherTest;
import com.yohwan.tutor.dto.request.TeacherUpdateRequest;
import com.yohwan.tutor.dto.response.TeacherResponse;
import com.yohwan.tutor.repository.TeacherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ServiceTest
class TeacherServiceTest extends TeacherTest {

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherService teacherService;

    @Test
    @DisplayName("나의 정보(선생님)를 조회한다.")
    void getMyInfo() {
        Teacher teacher = teacher();
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.ofNullable(teacher));

        TeacherResponse response = teacherService.getMyInfo(1L);

        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo(teacher.getName());
        assertThat(response.getMemo()).isEqualTo(teacher.getMemo());
        verify(teacherRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("나의 정보(선생님)를 조회 시 아이디가 옳바르지 않으면 예외를 발생한다.")
    void getMyInfoByNotExistedId() {
        long notExistedId = 9999999L;
        when(teacherRepository.findById(notExistedId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> teacherService.getMyInfo(notExistedId));
    }

    @Test
    @DisplayName("나의 정보(선생님)를 수정한다.")
    void updateMyInfo() {
        TeacherUpdateRequest request = updateRequest();
        Teacher teacher = teacher();
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.ofNullable(teacher));

        teacherService.updateMyInfo(request, 1L);

        assertThat(teacher.getName()).isEqualTo(request.getName());
        assertThat(teacher.getMemo()).isEqualTo(request.getMemo());
        verify(teacherRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("나의 정보(선생님)를 수정 시 아이디가 옳바르지 않으면 예외를 발생한다.")
    void updateMyInfoByNotExistedId() {
        TeacherUpdateRequest request = updateRequest();
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> teacherService.updateMyInfo(request, 1L));
    }
}