package com.yohwan.tutor.repository;

import com.yohwan.tutor.domain.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
