package com.yohwan.tutor.repository;

import com.yohwan.tutor.domain.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
