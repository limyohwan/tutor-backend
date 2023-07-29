package com.yohwan.tutor.repository;

import com.yohwan.tutor.domain.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
