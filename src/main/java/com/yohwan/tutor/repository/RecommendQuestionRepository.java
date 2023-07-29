package com.yohwan.tutor.repository;

import com.yohwan.tutor.domain.entity.Group;
import com.yohwan.tutor.domain.entity.RecommendQuestion;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommendQuestionRepository extends JpaRepository<RecommendQuestion, Long> {
    @EntityGraph(attributePaths = {"group", "question"})
    List<RecommendQuestion> findByGroup(Group group);
}
