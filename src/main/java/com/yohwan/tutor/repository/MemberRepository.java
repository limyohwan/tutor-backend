package com.yohwan.tutor.repository;

import com.yohwan.tutor.domain.entity.Group;
import com.yohwan.tutor.domain.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @EntityGraph(attributePaths = {"group", "student"})
    List<Member> findByGroup(Group group);
}
