package com.yohwan.tutor.repository;

import com.yohwan.tutor.data.QuestionTest;
import com.yohwan.tutor.annotation.RepositoryTest;
import com.yohwan.tutor.domain.entity.Question;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RepositoryTest
class QuestionRepositoryTest extends QuestionTest {
    @Autowired
    private QuestionRepository questionRepository;

    @Test
    @DisplayName("문제를 등록한다.")
    void saveQuestion() {
        Question question = question();

        Question savedQuestion = questionRepository.save(question);

        assertThat(savedQuestion.getLevel()).isEqualTo(question.getLevel());
        assertThat(savedQuestion.getDescription()).isEqualTo(question.getDescription());
    }

    @Test
    @DisplayName("특정 아이디로 문제를 조회한다.")
    void findByQuestionId() {
        Question savedQuestion = questionRepository.save(question());
        long questionId = savedQuestion.getId();

        Question foundQuestion = questionRepository.findById(questionId).orElseThrow(() -> {
            throw new IllegalArgumentException("존재하지 않은 아이디입니다.");
        });

        assertThat(savedQuestion.getLevel()).isEqualTo(foundQuestion.getLevel());
        assertThat(savedQuestion.getDescription()).isEqualTo(foundQuestion.getDescription());
    }

    @Test
    @DisplayName("특정 아이디로 문제를 조회 시 아이디가 옳바르지 않으면 예외를 발생한다.")
    void findByNotExistedQuestionId() {
        long notExistedId = 9999999L;

        assertThrows(IllegalArgumentException.class, () -> {
            questionRepository.findById(notExistedId).orElseThrow(() -> {
                throw new IllegalArgumentException("존재하지 않은 아이디입니다.");
            });
        });
    }
    @Test
    @DisplayName("문제들의 목록을 조회한다.")
    void findAllQuestions() {
        questionRepository.save(question());

        List<Question> questions = questionRepository.findAll();

        assertThat(questions).hasSize(1);
    }
}