package com.yohwan.tutor.service;

import com.yohwan.tutor.domain.entity.Student;
import com.yohwan.tutor.dto.request.StudentSaveRequest;
import com.yohwan.tutor.dto.request.StudentUpdateRequest;
import com.yohwan.tutor.dto.response.StudentResponse;
import com.yohwan.tutor.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Transactional(readOnly = true)
    public List<StudentResponse> listStudents() {
        return studentRepository.findAll().stream()
                .map(StudentResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public StudentResponse getStudent(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> {
            throw new IllegalArgumentException("존재하지 않는 아이디입니다.");
        });

        return new StudentResponse(student);
    }

    public StudentResponse saveStudent(StudentSaveRequest request) {
        Student savedStudent = studentRepository.save(Student.builder().name(request.getName()).build());
        return new StudentResponse(savedStudent);
    }

    public void updateStudent(Long studentId, StudentUpdateRequest request) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> {
            throw new IllegalArgumentException("존재하지 않는 아이디입니다.");
        });

        student.update(request.getName());
    }
}
