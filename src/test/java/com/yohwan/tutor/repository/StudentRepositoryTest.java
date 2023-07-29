package com.yohwan.tutor.repository;

import com.yohwan.tutor.annotation.RepositoryTest;
import com.yohwan.tutor.domain.entity.Student;
import com.yohwan.tutor.data.StudentTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RepositoryTest
class StudentRepositoryTest extends StudentTest {
    @Autowired
    private StudentRepository studentRepository;

    @Test
    @DisplayName("학생을 등록한다.")
    void saveStudent() {
        Student student = student();

        Student savedStudent = studentRepository.save(student);

        assertThat(savedStudent.getName()).isEqualTo(student.getName());
    }

    @Test
    @DisplayName("특정 아이디로 학생을 조회한다.")
    void findByStudentId() {
        Student savedStudent = studentRepository.save(student());
        long studentId = savedStudent.getId();

        Student foundStudent = studentRepository.findById(studentId).orElseThrow(() -> {
            throw new IllegalArgumentException("존재하지 않은 아이디입니다.");
        });

        assertThat(savedStudent.getName()).isEqualTo(foundStudent.getName());
    }

    @Test
    @DisplayName("특정 아이디로 학생을 조회 시 아이디가 옳바르지 않으면 예외를 발생한다.")
    void findByNotExistedStudentId() {
        long notExistedId = 9999999L;

        assertThrows(IllegalArgumentException.class, () -> {
            studentRepository.findById(notExistedId).orElseThrow(() -> {
                throw new IllegalArgumentException("존재하지 않은 아이디입니다.");
            });
        });
    }
    @Test
    @DisplayName("학생들의 목록을 조회한다.")
    void findAllStudents() {
        studentRepository.save(student());

        List<Student> students = studentRepository.findAll();

        assertThat(students).hasSize(1);
    }
}