package com.yohwan.tutor.controller;

import com.yohwan.tutor.global.auth.AuthenticationTeacher;
import com.yohwan.tutor.global.dto.ApiResponse;
import com.yohwan.tutor.domain.entity.Teacher;
import com.yohwan.tutor.dto.response.GroupResponse;
import com.yohwan.tutor.dto.response.QuestionResponse;
import com.yohwan.tutor.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/groups")
@RestController
public class GroupController {
    private final GroupService groupService;

    @GetMapping("/{groupId}")
    public ResponseEntity<ApiResponse<GroupResponse>> getGroup(@PathVariable Long groupId,
                                                               @AuthenticationTeacher Teacher authTeacher) {
        return ResponseEntity.ok(new ApiResponse<>(groupService.getGroup(groupId)));
    }

    @GetMapping("/{groupId}/recommend-questions")
    public ResponseEntity<ApiResponse<List<QuestionResponse>>> listRecommendQuestions(@PathVariable Long groupId,
                                                                                      @AuthenticationTeacher Teacher authTeacher) {
        return ResponseEntity.ok(new ApiResponse<>(groupService.listRecommendQuestions(groupId)));
    }
}
