package com.yohwan.tutor.service;

import com.yohwan.tutor.domain.entity.Question;
import com.yohwan.tutor.dto.request.QuestionSaveRequest;
import com.yohwan.tutor.dto.request.QuestionUpdateRequest;
import com.yohwan.tutor.dto.response.QuestionResponse;
import com.yohwan.tutor.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    @Transactional(readOnly = true)
    public List<QuestionResponse> listQuestions() {
        return questionRepository.findAll().stream()
                .map(QuestionResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public QuestionResponse getQuestion(Long questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> {
            throw new IllegalArgumentException("존재하지 않는 아이디입니다.");
        });

        return new QuestionResponse(question);
    }

    public QuestionResponse saveQuestion(QuestionSaveRequest request) {
        Question savedQuestion = questionRepository.save(Question.builder().level(request.getLevel()).description(request.getDescription()).build());
        return new QuestionResponse(savedQuestion);
    }

    public void updateQuestion(Long questionId, QuestionUpdateRequest request) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> {
            throw new IllegalArgumentException("존재하지 않는 아이디입니다.");
        });

        question.update(request.getLevel(), request.getDescription());
    }
}
