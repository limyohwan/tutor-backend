package com.yohwan.tutor.repository;

import com.yohwan.tutor.annotation.RepositoryTest;
import com.yohwan.tutor.domain.entity.Group;
import com.yohwan.tutor.domain.entity.Member;
import com.yohwan.tutor.domain.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void findByGroup() {
        Group savedGroup = groupRepository.save(group());
        Student savedStudent = studentRepository.save(student());
        memberRepository.save(Member.builder().group(savedGroup).student(savedStudent).build());

        List<Member> members = memberRepository.findByGroup(savedGroup);
        assertThat(members).hasSize(1);
        assertThat(members.get(0).getGroup().getId()).isEqualTo(savedGroup.getId());
        assertThat(members.get(0).getStudent().getId()).isEqualTo(savedStudent.getId());
    }

    private Group group() {
        return Group.builder().name("그룹이름").standardDescription("랜덤").build();
    }

    private Student student() {
        return Student.builder().name("임요환").build();
    }
}