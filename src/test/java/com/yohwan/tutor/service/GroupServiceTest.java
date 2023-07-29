package com.yohwan.tutor.service;

import com.yohwan.tutor.annotation.ServiceTest;
import com.yohwan.tutor.data.GroupTest;
import com.yohwan.tutor.domain.entity.Group;
import com.yohwan.tutor.domain.entity.RecommendQuestion;
import com.yohwan.tutor.dto.response.GroupResponse;
import com.yohwan.tutor.dto.response.QuestionResponse;
import com.yohwan.tutor.repository.GroupRepository;
import com.yohwan.tutor.repository.MemberRepository;
import com.yohwan.tutor.repository.RecommendQuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ServiceTest
class GroupServiceTest extends GroupTest {

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private RecommendQuestionRepository recommendQuestionRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private GroupService groupService;

    @Test
    @DisplayName("그룹 정보를 조회한다.")
    void getGroup() {
        Group group = group();
        when(groupRepository.findById(anyLong())).thenReturn(Optional.ofNullable(group));
        when(memberRepository.findByGroup(group)).thenReturn(Collections.singletonList(member()));

        GroupResponse response = groupService.getGroup(1L);

        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo(group.getName());
        assertThat(response.getStandardDescription()).isEqualTo(group.getStandardDescription());
        assertThat(response.getStudents()).hasSize(1);
        verify(groupRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("그룹 정보를 조회 시 아이디가 옳바르지 않으면 예외를 발생한다.")
    void getGroupByNotExistedId() {
        long notExistedId = 9999999L;
        when(groupRepository.findById(notExistedId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> groupService.getGroup(notExistedId));
    }

    @Test
    @DisplayName("그룹에서 추천하는 문제 목록을 조회한다.")
    void listRecommendQuestions() {
        Group group = group();
        when(groupRepository.findById(anyLong())).thenReturn(Optional.ofNullable(group));
        RecommendQuestion recommendQuestion = recommendQuestion();
        when(recommendQuestionRepository.findByGroup(group)).thenReturn(Collections.singletonList(recommendQuestion));

        List<QuestionResponse> responses = groupService.listRecommendQuestions(1L);

        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).getLevel()).isEqualTo(recommendQuestion.getQuestion().getLevel());
        assertThat(responses.get(0).getDescription()).isEqualTo(recommendQuestion.getQuestion().getDescription());
        verify(groupRepository, times(1)).findById(anyLong());
        verify(recommendQuestionRepository, times(1)).findByGroup(group);
    }

    @Test
    @DisplayName("그룹에서 추천하는 문제 목록을 조회 시 문제가 0개이면 예외를 발생한다.")
    void listNone() {
        Group group = group();
        when(groupRepository.findById(anyLong())).thenReturn(Optional.ofNullable(group));
        RecommendQuestion recommendQuestion = recommendQuestion();
        when(recommendQuestionRepository.findByGroup(group)).thenReturn(Collections.emptyList());

        assertThrows(IllegalStateException.class, () -> groupService.listRecommendQuestions(1L));
    }

    @Test
    @DisplayName("그룹에서 추천하는 문제 목록을 조회 시 아이디가 옳바르지 않으면 예외를 발생한다.")
    void listRecommendQuestionsByNotExistedId() {
        long notExistedId = 9999999L;
        when(groupRepository.findById(notExistedId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> groupService.listRecommendQuestions(notExistedId));
    }
}