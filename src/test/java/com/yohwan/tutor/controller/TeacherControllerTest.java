package com.yohwan.tutor.controller;

import com.yohwan.tutor.annotation.ControllerTest;
import com.yohwan.tutor.domain.entity.Teacher;
import com.yohwan.tutor.data.TeacherTest;
import com.yohwan.tutor.dto.request.TeacherUpdateRequest;
import com.yohwan.tutor.dto.response.TeacherResponse;
import com.yohwan.tutor.repository.TeacherRepository;
import com.yohwan.tutor.service.TeacherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest(TeacherController.class)
class TeacherControllerTest extends TeacherTest {
    private static final String BASE_URL = "/api/teachers";

    @MockBean
    private TeacherService teacherService;

    @MockBean
    private TeacherRepository teacherRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("나의 정보(선생님)를 조회한다.")
    void getMyInfo() throws Exception {
        long teacherId = 1L;
        Teacher teacher = teacher();
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.ofNullable(teacher));
        TeacherResponse response = teacherResponse(teacherId);
        when(teacherService.getMyInfo(teacher.getId())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/me")
                        .header(HttpHeaders.AUTHORIZATION, teacherId)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value(teacher.getName()))
                .andExpect(jsonPath("$.data.memo").value(teacher.getMemo()));

        verify(teacherRepository, times(1)).findById(teacherId);
        verify(teacherService, times(1)).getMyInfo(teacher.getId());
    }

    @Test
    @DisplayName("나의 정보(선생님)를 수정한다.")
    void updateMyInfo() throws Exception {
        long teacherId = 1L;
        Teacher teacher = teacher();
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.ofNullable(teacher));
        TeacherUpdateRequest request = updateRequest();

        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/me")
                        .header(HttpHeaders.AUTHORIZATION, teacherId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());

        verify(teacherRepository, times(1)).findById(teacherId);
        verify(teacherService, times(1)).updateMyInfo(any(TeacherUpdateRequest.class), any());
    }
}