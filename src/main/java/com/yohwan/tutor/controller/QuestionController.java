package com.yohwan.tutor.controller;

import com.yohwan.tutor.global.auth.AuthenticationTeacher;
import com.yohwan.tutor.global.dto.ApiResponse;
import com.yohwan.tutor.domain.entity.Teacher;
import com.yohwan.tutor.dto.request.QuestionSaveRequest;
import com.yohwan.tutor.dto.request.QuestionUpdateRequest;
import com.yohwan.tutor.dto.response.QuestionResponse;
import com.yohwan.tutor.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/questions")
@RestController
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<QuestionResponse>>> listQuestions(@AuthenticationTeacher Teacher authTeacher) {
        return ResponseEntity.ok(new ApiResponse<>(questionService.listQuestions()));
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<ApiResponse<QuestionResponse>> getQuestion(@PathVariable Long questionId,
                                                                     @AuthenticationTeacher Teacher authTeacher) {
        return ResponseEntity.ok(new ApiResponse<>(questionService.getQuestion(questionId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<QuestionResponse>> saveQuestion(@RequestBody QuestionSaveRequest request,
                                                                      @AuthenticationTeacher Teacher authTeacher) {
        QuestionResponse response = questionService.saveQuestion(request);
        return ResponseEntity.created(URI.create("/api/questions/" + response.getId())).body(new ApiResponse<>(response));
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<Void> updateQuestion(@PathVariable Long questionId,
                                               @RequestBody QuestionUpdateRequest request,
                                               @AuthenticationTeacher Teacher authTeacher) {
        questionService.updateQuestion(questionId, request);
        return ResponseEntity.noContent().build();
    }
}
