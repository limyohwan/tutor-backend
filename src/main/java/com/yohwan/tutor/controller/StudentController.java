package com.yohwan.tutor.controller;

import com.yohwan.tutor.global.auth.AuthenticationTeacher;
import com.yohwan.tutor.global.dto.ApiResponse;
import com.yohwan.tutor.domain.entity.Teacher;
import com.yohwan.tutor.dto.request.StudentSaveRequest;
import com.yohwan.tutor.dto.request.StudentUpdateRequest;
import com.yohwan.tutor.dto.response.StudentResponse;
import com.yohwan.tutor.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/students")
@RestController
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentResponse>>> listStudents(@AuthenticationTeacher Teacher authTeacher) {
        return ResponseEntity.ok(new ApiResponse<>(studentService.listStudents()));
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<ApiResponse<StudentResponse>> getStudent(@PathVariable Long studentId,
                                                                   @AuthenticationTeacher Teacher authTeacher) {
        return ResponseEntity.ok(new ApiResponse<>(studentService.getStudent(studentId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<StudentResponse>> saveStudent(@RequestBody StudentSaveRequest request,
                                                                    @AuthenticationTeacher Teacher authTeacher) {
        StudentResponse response = studentService.saveStudent(request);
        return ResponseEntity.created(URI.create("/api/students/" + response.getId())).body(new ApiResponse<>(response));
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<Void> updateStudent(@PathVariable Long studentId,
                                              @RequestBody StudentUpdateRequest request,
                                              @AuthenticationTeacher Teacher authTeacher) {
        studentService.updateStudent(studentId, request);
        return ResponseEntity.noContent().build();
    }
}
