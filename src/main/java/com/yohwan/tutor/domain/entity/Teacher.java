package com.yohwan.tutor.domain.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private Long id;
    private String name;
    private String memo;

    @Builder
    public Teacher(String name, String memo) {
        this.name = name;
        this.memo = memo;
    }

    public void update(String name, String memo) {
        this.name = name;
        this.memo = memo;
    }
}
