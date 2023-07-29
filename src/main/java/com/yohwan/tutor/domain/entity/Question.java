package com.yohwan.tutor.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;
    private int level;
    private String description;

    @Builder
    public Question(int level, String description) {
        this.level = level;
        this.description = description;
    }

    public void update(int level, String description) {
        this.level = level;
        this.description = description;
    }
}
