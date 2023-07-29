package com.yohwan.tutor.repository;

import com.yohwan.tutor.annotation.RepositoryTest;
import com.yohwan.tutor.domain.entity.Teacher;
import com.yohwan.tutor.data.TeacherTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RepositoryTest
class TeacherRepositoryTest extends TeacherTest {

    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    @DisplayName("선생님을 등록한다.")
    void saveTeacher() {
        Teacher teacher = teacher();

        Teacher savedTeacher = teacherRepository.save(teacher);

        assertThat(savedTeacher.getName()).isEqualTo(teacher.getName());
        assertThat(savedTeacher.getMemo()).isEqualTo(teacher.getMemo());
    }

    @Test
    @DisplayName("특정 아이디로 선생님을 조회한다.")
    void findByTeacherId() {
        Teacher savedTeacher = teacherRepository.save(teacher());
        long teacherId = savedTeacher.getId();

        Teacher foundTeacher = teacherRepository.findById(teacherId).orElseThrow(() -> {
            throw new IllegalArgumentException("존재하지 않은 아이디입니다.");
        });

        assertThat(savedTeacher.getName()).isEqualTo(foundTeacher.getName());
        assertThat(savedTeacher.getMemo()).isEqualTo(foundTeacher.getMemo());
    }

    @Test
    @DisplayName("특정 아이디로 선생님을 조회 시 아이디가 옳바르지 않으면 예외를 발생한다.")
    void findByNotExistedTeacherId() {
        long notExistedId = 9999999L;

        assertThrows(IllegalArgumentException.class, () -> {
            teacherRepository.findById(notExistedId).orElseThrow(() -> {
                throw new IllegalArgumentException("존재하지 않은 아이디입니다.");
            });
        });
    }
    @Test
    @DisplayName("선생님들의 목록을 조회한다.")
    void findAllTeachers() {
        teacherRepository.save(teacher());

        List<Teacher> teachers = teacherRepository.findAll();

        assertThat(teachers).hasSize(1);
    }

}