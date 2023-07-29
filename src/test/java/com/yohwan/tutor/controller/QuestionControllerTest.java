package com.yohwan.tutor.controller;

import com.yohwan.tutor.annotation.ControllerTest;
import com.yohwan.tutor.data.QuestionTest;
import com.yohwan.tutor.domain.entity.Teacher;
import com.yohwan.tutor.dto.request.QuestionSaveRequest;
import com.yohwan.tutor.dto.request.QuestionUpdateRequest;
import com.yohwan.tutor.dto.response.QuestionResponse;
import com.yohwan.tutor.repository.TeacherRepository;
import com.yohwan.tutor.service.QuestionService;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest(QuestionController.class)
class QuestionControllerTest extends QuestionTest {
    private static final String BASE_URL = "/api/questions";

    @MockBean
    private QuestionService questionService;

    @MockBean
    private TeacherRepository teacherRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("문제들의 목록을 조회한다.")
    void listQuestions() throws Exception {
        long teacherId = 1L;
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.ofNullable(teacher()));
        QuestionResponse response = questionResponse();
        when(questionService.listQuestions()).thenReturn(Collections.singletonList(response));

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL)
                        .header(HttpHeaders.AUTHORIZATION, teacherId)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data[0].id").value(response.getId()))
                .andExpect(jsonPath("$.data[0].level").value(response.getLevel()))
                .andExpect(jsonPath("$.data[0].description").value(response.getDescription()));

        verify(teacherRepository, times(1)).findById(anyLong());
        verify(questionService, times(1)).listQuestions();
    }

    @Test
    @DisplayName("문제를 조회한다.")
    void getQuestion() throws Exception {
        long teacherId = 1L;
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.ofNullable(teacher()));
        long questionId = 1L;
        QuestionResponse response = questionResponse();
        when(questionService.getQuestion(questionId)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/" + questionId)
                        .header(HttpHeaders.AUTHORIZATION, teacherId)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(response.getId()))
                .andExpect(jsonPath("$.data.level").value(response.getLevel()))
                .andExpect(jsonPath("$.data.description").value(response.getDescription()));

        verify(teacherRepository, times(1)).findById(anyLong());
        verify(questionService, times(1)).getQuestion(questionId);
    }

    @Test
    @DisplayName("문제를 등록한다.")
    void saveQuestion() throws Exception {
        long teacherId = 1L;
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.ofNullable(teacher()));
        QuestionSaveRequest request = saveRequest();
        QuestionResponse response = new QuestionResponse(1L, request.getLevel(), request.getDescription());
        when(questionService.saveQuestion(any(QuestionSaveRequest.class))).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .header(HttpHeaders.AUTHORIZATION, teacherId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.level").value(request.getLevel()))
                .andExpect(jsonPath("$.data.description").value(request.getDescription()));

        verify(teacherRepository, times(1)).findById(anyLong());
        verify(questionService, times(1)).saveQuestion(any(QuestionSaveRequest.class));
    }

    @Test
    @DisplayName("문제를 수정한다.")
    void updateQuestion() throws Exception {
        long teacherId = 1L;
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.ofNullable(teacher()));
        QuestionUpdateRequest request = updateRequest();
        long questionId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/" + questionId)
                        .header(HttpHeaders.AUTHORIZATION, teacherId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());

        verify(teacherRepository, times(1)).findById(anyLong());
        verify(questionService, times(1)).updateQuestion(anyLong(), any(QuestionUpdateRequest.class));
    }

    private Teacher teacher() {
        return Teacher.builder().name("임요환").memo("열혈선생님").build();
    }

}