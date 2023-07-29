package com.yohwan.tutor.controller;

import com.yohwan.tutor.annotation.ControllerTest;
import com.yohwan.tutor.domain.entity.Teacher;
import com.yohwan.tutor.data.GroupTest;
import com.yohwan.tutor.dto.response.GroupResponse;
import com.yohwan.tutor.dto.response.QuestionResponse;
import com.yohwan.tutor.repository.TeacherRepository;
import com.yohwan.tutor.service.GroupService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest(GroupController.class)
class GroupControllerTest extends GroupTest {
    private static final String BASE_URL = "/api/groups";

    @MockBean
    private GroupService groupService;

    @MockBean
    private TeacherRepository teacherRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("인증 헤더에 들어온 값이 숫자형식이 아니면 예외를 발생한다.")
    void authorizationHeaderFormatIsNotNumberThrowsException() throws Exception {
        long groupId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/" + groupId)
                        .header(HttpHeaders.AUTHORIZATION, "string")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("인증 헤더에 값이 없으면 예외를 발생한다.")
    void authorizationHeaderHasNoValueThrowsException() throws Exception {
        long groupId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/" + groupId)
                        .header(HttpHeaders.AUTHORIZATION, "")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("인증 헤더에 나의 정보(선생님)가 옳바르지 않으면 예외를 발생한다")
    void authorizationHeaderHasNotExistedTeacherIdThrowsException() throws Exception {
        long groupId = 1L;
        long notExistedId = 9999999L;
        when(teacherRepository.findById(notExistedId)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/" + groupId)
                        .header(HttpHeaders.AUTHORIZATION, notExistedId)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("그룹 정보를 조회한다.")
    void getGroup() throws Exception {
        long teacherId = 1L;
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.ofNullable(teacher()));
        long groupId = 1L;
        GroupResponse response = groupResponse(groupId);
        when(groupService.getGroup(groupId)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/" + groupId)
                        .header(HttpHeaders.AUTHORIZATION, teacherId)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(response.getId()))
                .andExpect(jsonPath("$.data.name").value(response.getName()))
                .andExpect(jsonPath("$.data.standardDescription").value(response.getStandardDescription()))
                .andExpect(jsonPath("$.data.students").isEmpty());

        verify(teacherRepository, times(1)).findById(anyLong());
        verify(groupService, times(1)).getGroup(groupId);
    }

    @Test
    @DisplayName("그룹 정보를 조회 시 아이디가 옳바르지 않으면 예외를 발생한다.")
    void getGroupByNotExistedId() throws Exception {
        long teacherId = 1L;
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.ofNullable(teacher()));
        long notExistedId = 99999L;
        when(groupService.getGroup(notExistedId)).thenThrow(new IllegalArgumentException());

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/" + notExistedId)
                        .header(HttpHeaders.AUTHORIZATION, teacherId)
                )
                .andExpect(status().isBadRequest());

        verify(teacherRepository, times(1)).findById(anyLong());
        verify(groupService, times(1)).getGroup(notExistedId);
    }

    @Test
    @DisplayName("그룹에서 추천하는 문제 목록을 조회한다.")
    void listRecommendQuestions() throws Exception {
        long teacherId = 1L;
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.ofNullable(teacher()));
        long groupId = 1L;
        QuestionResponse response = questionResponse();
        when(groupService.listRecommendQuestions(groupId)).thenReturn(Collections.singletonList(response));

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/" + groupId + "/recommend-questions")
                        .header(HttpHeaders.AUTHORIZATION, teacherId)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data[0].id").value(response.getId()))
                .andExpect(jsonPath("$.data[0].level").value(response.getLevel()))
                .andExpect(jsonPath("$.data[0].description").value(response.getDescription()));

        verify(teacherRepository, times(1)).findById(anyLong());
        verify(groupService, times(1)).listRecommendQuestions(groupId);
    }

    private Teacher teacher() {
        return Teacher.builder().name("임요환").memo("열혈선생님").build();
    }

}