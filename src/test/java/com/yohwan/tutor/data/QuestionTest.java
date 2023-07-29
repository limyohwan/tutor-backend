package com.yohwan.tutor.data;

import com.yohwan.tutor.domain.entity.Question;
import com.yohwan.tutor.dto.request.QuestionSaveRequest;
import com.yohwan.tutor.dto.request.QuestionUpdateRequest;
import com.yohwan.tutor.dto.response.QuestionResponse;

import java.util.ArrayList;
import java.util.List;

public class QuestionTest {
    protected Question question() {
        return Question.builder().level(1).description("쉬운문제").build();
    }

    protected List<Question> questionList() {
        List<Question> result = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            result.add(Question.builder().level(i+1).description("임요환이 낸 문제").build());
        }
        return result;
    }

    protected QuestionResponse questionResponse() {
        return new QuestionResponse(1L, 1, "쉬운문제");
    }

    protected QuestionSaveRequest saveRequest() {
        return new QuestionSaveRequest(1, "임요환이 낸 문제");
    }

    protected QuestionUpdateRequest updateRequest() {
        return new QuestionUpdateRequest(2, "임요환2가 낸 문제");
    }
}
