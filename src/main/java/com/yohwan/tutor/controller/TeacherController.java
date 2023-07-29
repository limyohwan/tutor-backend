package com.yohwan.tutor.controller;

import com.yohwan.tutor.global.auth.AuthenticationTeacher;
import com.yohwan.tutor.global.dto.ApiResponse;
import com.yohwan.tutor.domain.entity.Teacher;
import com.yohwan.tutor.dto.request.TeacherUpdateRequest;
import com.yohwan.tutor.dto.response.TeacherResponse;
import com.yohwan.tutor.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/teachers")
@RestController
public class TeacherController {
    private final TeacherService teacherService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<TeacherResponse>> getMyInfo(@AuthenticationTeacher Teacher authTeacher){
        return ResponseEntity.ok(new ApiResponse<>(teacherService.getMyInfo(authTeacher.getId())));
    }

    @PutMapping("/me")
    public ResponseEntity<Void> updateMyInfo(@RequestBody TeacherUpdateRequest request,
                                             @AuthenticationTeacher Teacher authTeacher){
        teacherService.updateMyInfo(request, authTeacher.getId());
        return ResponseEntity.noContent().build();
    }
}
