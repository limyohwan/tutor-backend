package com.yohwan.tutor.dto.response;

import com.yohwan.tutor.domain.entity.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuestionResponse {
    private Long id;
    private int level;
    private String description;

    public QuestionResponse(Question question) {
        this(question.getId(), question.getLevel(), question.getDescription());
    }

    public QuestionResponse(Long id, int level, String description) {
        this.id = id;
        this.level = level;
        this.description = description;
    }
}
