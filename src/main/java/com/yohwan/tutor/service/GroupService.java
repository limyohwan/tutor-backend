package com.yohwan.tutor.service;

import com.yohwan.tutor.domain.entity.Group;
import com.yohwan.tutor.domain.entity.Member;
import com.yohwan.tutor.domain.entity.RecommendQuestion;
import com.yohwan.tutor.dto.response.GroupResponse;
import com.yohwan.tutor.dto.response.QuestionResponse;
import com.yohwan.tutor.repository.GroupRepository;
import com.yohwan.tutor.repository.MemberRepository;
import com.yohwan.tutor.repository.RecommendQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final RecommendQuestionRepository recommendQuestionRepository;
    private final MemberRepository memberRepository;


    @Transactional(readOnly = true)
    public GroupResponse getGroup(Long groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> {
            throw new IllegalArgumentException("존재하지 않는 아이디입니다.");
        });

        List<Member> members = memberRepository.findByGroup(group);

        return new GroupResponse(group, members);
    }

    @Transactional(readOnly = true)
    public List<QuestionResponse> listRecommendQuestions(Long groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> {
            throw new IllegalArgumentException("존재하지 않는 아이디입니다.");
        });

        List<RecommendQuestion> questions = recommendQuestionRepository.findByGroup(group);

        if(questions.isEmpty()) {
            throw new IllegalStateException("그룹에는 하나 이상의 추천 문제가 포함되어야 합니다.");
        }

        return questions.stream()
                .map(recommendQuestion -> new QuestionResponse(recommendQuestion.getQuestion()))
                .collect(Collectors.toList());
    }
}
