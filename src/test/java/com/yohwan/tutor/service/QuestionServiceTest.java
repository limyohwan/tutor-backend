package com.yohwan.tutor.service;

import com.yohwan.tutor.data.QuestionTest;
import com.yohwan.tutor.annotation.ServiceTest;
import com.yohwan.tutor.domain.entity.Question;
import com.yohwan.tutor.dto.request.QuestionSaveRequest;
import com.yohwan.tutor.dto.request.QuestionUpdateRequest;
import com.yohwan.tutor.dto.response.QuestionResponse;
import com.yohwan.tutor.repository.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ServiceTest
class QuestionServiceTest extends QuestionTest {

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuestionService questionService;

    @Test
    @DisplayName("문제들의 목록을 조회한다.(5개)")
    void listQuestions() {
        List<Question> questions = questionList();
        when(questionRepository.findAll()).thenReturn(questions);

        List<QuestionResponse> responses = questionService.listQuestions();

        assertThat(responses).hasSize(5);
        for (int i = 0; i < responses.size(); i++) {
            assertThat(responses.get(i).getLevel()).isEqualTo(questions.get(i).getLevel());
            assertThat(responses.get(i).getDescription()).isEqualTo(questions.get(i).getDescription());
        }
        verify(questionRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("문제들의 목록을 조회한다.(1개)")
    void listOneQuestion() {
        Question question = question();
        when(questionRepository.findAll()).thenReturn(Collections.singletonList(question));

        List<QuestionResponse> responses = questionService.listQuestions();

        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).getLevel()).isEqualTo(question.getLevel());
        assertThat(responses.get(0).getDescription()).isEqualTo(question.getDescription());
        verify(questionRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("문제들의 목록을 조회한다.(0개)")
    void listNone() {
        when(questionRepository.findAll()).thenReturn(Collections.emptyList());

        List<QuestionResponse> responses = questionService.listQuestions();

        assertThat(responses).hasSize(0);
        verify(questionRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("문제를 조회한다.")
    void getQuestion() {
        Question question = question();
        when(questionRepository.findById(anyLong())).thenReturn(Optional.ofNullable(question));

        QuestionResponse response = questionService.getQuestion(1L);

        assertThat(response).isNotNull();
        assertThat(response.getLevel()).isEqualTo(question.getLevel());
        assertThat(response.getDescription()).isEqualTo(question.getDescription());
        verify(questionRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("문제를 조회 시 아이디가 옳바르지 않으면 예외를 발생한다.")
    void getQuestionByNotExistedId() {
        long notExistedId = 9999999L;
        when(questionRepository.findById(notExistedId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> questionService.getQuestion(notExistedId));
    }

    @Test
    @DisplayName("문제를 등록한다.")
    void saveQuestion() {
        QuestionSaveRequest request = saveRequest();
        when(questionRepository.save(any(Question.class))).thenReturn(Question.builder().level(request.getLevel()).description(request.getDescription()).build());

        QuestionResponse response = questionService.saveQuestion(request);

        assertThat(request.getLevel()).isEqualTo(response.getLevel());
        assertThat(request.getDescription()).isEqualTo(response.getDescription());
        verify(questionRepository, times(1)).save(any(Question.class));
    }

    @Test
    @DisplayName("문제를 수정한다.")
    void updateQuestion() {
        QuestionUpdateRequest request = updateRequest();
        Question question = question();
        when(questionRepository.findById(anyLong())).thenReturn(Optional.ofNullable(question));

        questionService.updateQuestion(1L, request);

        assertThat(request.getLevel()).isEqualTo(question.getLevel());
        assertThat(request.getDescription()).isEqualTo(question.getDescription());
        verify(questionRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("문제를 수정 시 아이디가 옳바르지 않으면 예외를 발생한다.")
    void updateQuestionByNotExistedId() {
        QuestionUpdateRequest request = updateRequest();
        when(questionRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> questionService.updateQuestion(1L, request));
    }

}