package com.yohwan.tutor.controller;

import com.yohwan.tutor.annotation.ControllerTest;
import com.yohwan.tutor.domain.entity.Teacher;
import com.yohwan.tutor.data.StudentTest;
import com.yohwan.tutor.dto.request.StudentSaveRequest;
import com.yohwan.tutor.dto.request.StudentUpdateRequest;
import com.yohwan.tutor.dto.response.StudentResponse;
import com.yohwan.tutor.repository.TeacherRepository;
import com.yohwan.tutor.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest(StudentController.class)
class StudentControllerTest extends StudentTest {
    private static final String BASE_URL = "/api/students";

    @MockBean
    private StudentService studentService;

    @MockBean
    private TeacherRepository teacherRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("학생들의 목록을 조회한다.")
    void listStudents() throws Exception {
        long teacherId = 1L;
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.ofNullable(teacher()));
        StudentResponse response = studentResponse();
        when(studentService.listStudents()).thenReturn(Collections.singletonList(response));

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL)
                        .header(HttpHeaders.AUTHORIZATION, teacherId)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data[0].id").value(response.getId()))
                .andExpect(jsonPath("$.data[0].name").value(response.getName()));

        verify(teacherRepository, times(1)).findById(anyLong());
        verify(studentService, times(1)).listStudents();
    }

    @Test
    @DisplayName("학생 정보를 조회한다.")
    void getStudent() throws Exception {
        long teacherId = 1L;
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.ofNullable(teacher()));
        long studentId = 1L;
        StudentResponse response = studentResponse();
        when(studentService.getStudent(studentId)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/" + studentId)
                        .header(HttpHeaders.AUTHORIZATION, teacherId)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(response.getId()))
                .andExpect(jsonPath("$.data.name").value(response.getName()));

        verify(teacherRepository, times(1)).findById(anyLong());
        verify(studentService, times(1)).getStudent(studentId);
    }

    @Test
    @DisplayName("학생 정보를 등록한다.")
    void saveStudent() throws Exception {
        long teacherId = 1L;
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.ofNullable(teacher()));
        StudentSaveRequest request = saveRequest();
        StudentResponse response = new StudentResponse(1L, request.getName());
        when(studentService.saveStudent(any(StudentSaveRequest.class))).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .header(HttpHeaders.AUTHORIZATION, teacherId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value(request.getName()));

        verify(teacherRepository, times(1)).findById(anyLong());
        verify(studentService, times(1)).saveStudent(any(StudentSaveRequest.class));
    }

    @Test
    @DisplayName("학생 정보를 수정한다.")
    void updateStudent() throws Exception {
        long teacherId = 1L;
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.ofNullable(teacher()));
        StudentUpdateRequest request = updateRequest();
        long questionId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/" + questionId)
                        .header(HttpHeaders.AUTHORIZATION, teacherId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());

        verify(teacherRepository, times(1)).findById(anyLong());
        verify(studentService, times(1)).updateStudent(anyLong(), any(StudentUpdateRequest.class));
    }

    private Teacher teacher() {
        return Teacher.builder().name("임요환").memo("열혈선생님").build();
    }

}