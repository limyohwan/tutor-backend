package com.yohwan.tutor.repository;

import com.yohwan.tutor.annotation.RepositoryTest;
import com.yohwan.tutor.domain.entity.Group;
import com.yohwan.tutor.domain.entity.Question;
import com.yohwan.tutor.domain.entity.RecommendQuestion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
class RecommendQuestionRepositoryTest {
    @Autowired
    private RecommendQuestionRepository recommendQuestionRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    @DisplayName("그룹에 속해있는 추천 문제들을 조회한다.")
    void findByGroup() {
        Group savedGroup = groupRepository.save(group());
        Question savedQuestion = questionRepository.save(question());
        recommendQuestionRepository.save(RecommendQuestion.builder().group(savedGroup).question(savedQuestion).build());

        List<RecommendQuestion> recommendQuestions = recommendQuestionRepository.findByGroup(savedGroup);
        assertThat(recommendQuestions).hasSize(1);
        assertThat(recommendQuestions.get(0).getGroup().getId()).isEqualTo(savedGroup.getId());
        assertThat(recommendQuestions.get(0).getQuestion().getId()).isEqualTo(savedQuestion.getId());
    }

    private Group group() {
        return Group.builder().name("그룹이름").standardDescription("랜덤").build();
    }

    private Question question() {
        return Question.builder().level(1).description("쉬운 문제").build();
    }
}