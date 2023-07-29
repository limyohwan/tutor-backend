package com.yohwan.tutor.data;

import com.yohwan.tutor.domain.entity.*;
import com.yohwan.tutor.dto.response.GroupResponse;
import com.yohwan.tutor.dto.response.QuestionResponse;

import java.util.Collections;

public class GroupTest {
    protected Group group() {
        return Group.builder().name("그룹이름").standardDescription("랜덤").build();
    }

    protected Student student() {
        return Student.builder().name("임요환").build();
    }

    protected Member member() {
        return Member.builder().group(group()).student(student()).build();
    }

    protected Question question() {
        return Question.builder().level(1).description("쉬운문제").build();
    }

    protected RecommendQuestion recommendQuestion() {
        return RecommendQuestion.builder().group(group()).question(question()).build();
    }

    protected GroupResponse groupResponse(long groupId) {
        return new GroupResponse(groupId, "그룹이름", "설명", Collections.emptyList());
    }

    protected QuestionResponse questionResponse() {
        return new QuestionResponse(1L, 1, "쉬운문제");
    }
}
