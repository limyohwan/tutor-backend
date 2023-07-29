package com.yohwan.tutor.service;

import com.yohwan.tutor.annotation.ServiceTest;
import com.yohwan.tutor.domain.entity.Student;
import com.yohwan.tutor.data.StudentTest;
import com.yohwan.tutor.dto.request.StudentSaveRequest;
import com.yohwan.tutor.dto.request.StudentUpdateRequest;
import com.yohwan.tutor.dto.response.StudentResponse;
import com.yohwan.tutor.repository.StudentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ServiceTest
class StudentServiceTest extends StudentTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    @DisplayName("학생들의 목록을 조회한다.(5명)")
    void listStudents() {
        List<Student> students = studentList();
        when(studentRepository.findAll()).thenReturn(students);

        List<StudentResponse> responses = studentService.listStudents();

        assertThat(responses).hasSize(5);
        for (int i = 0; i < responses.size(); i++) {
            assertThat(responses.get(i).getName()).isEqualTo(students.get(i).getName());
        }
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("학생들의 목록을 조회한다.(1명)")
    void listOneStudent() {
        Student student = student();
        when(studentRepository.findAll()).thenReturn(Collections.singletonList(student));

        List<StudentResponse> responses = studentService.listStudents();

        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).getName()).isEqualTo(student.getName());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("학생들의 목록을 조회한다.(0명)")
    void listNone() {
        when(studentRepository.findAll()).thenReturn(Collections.emptyList());

        List<StudentResponse> responses = studentService.listStudents();

        assertThat(responses).hasSize(0);
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("학생 정보를 조회한다.")
    void getStudent() {
        Student student = student();
        when(studentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(student));

        StudentResponse response = studentService.getStudent(1L);

        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo(student.getName());
        verify(studentRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("학생 정보를 조회 시 아이디가 옳바르지 않으면 예외를 발생한다.")
    void getStudentByNotExistedId() {
        long notExistedId = 9999999L;
        when(studentRepository.findById(notExistedId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> studentService.getStudent(notExistedId));
    }

    @Test
    @DisplayName("학생 정보를 등록한다.")
    void saveStudent() {
        StudentSaveRequest request = saveRequest();
        when(studentRepository.save(any(Student.class))).thenReturn(Student.builder().name(request.getName()).build());

        StudentResponse response = studentService.saveStudent(request);

        assertThat(request.getName()).isEqualTo(response.getName());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    @DisplayName("학생 정보를 수정한다.")
    void updateStudent() {
        StudentUpdateRequest request = updateRequest();
        Student student = student();
        when(studentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(student));

        studentService.updateStudent(1L, request);

        assertThat(request.getName()).isEqualTo(student.getName());
        verify(studentRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("학생 정보를 수정 시 아이디가 옳바르지 않으면 예외를 발생한다.")
    void updateStudentByNotExistedId() {
        StudentUpdateRequest request = updateRequest();
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> studentService.updateStudent(1L, request));
    }
}