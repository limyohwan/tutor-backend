package com.yohwan.tutor.repository;

import com.yohwan.tutor.annotation.RepositoryTest;
import com.yohwan.tutor.domain.entity.Group;
import com.yohwan.tutor.data.GroupTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RepositoryTest
class GroupRepositoryTest extends GroupTest {
    @Autowired
    private GroupRepository groupRepository;

    @Test
    @DisplayName("그룹을 등록한다.")
    void saveGroup() {
        Group group = group();

        Group savedGroup = groupRepository.save(group);

        assertThat(savedGroup.getName()).isEqualTo(group.getName());
        assertThat(savedGroup.getStandardDescription()).isEqualTo(group.getStandardDescription());
    }

    @Test
    @DisplayName("특정 아이디로 그룹을 조회한다.")
    void findByGroupId() {
        Group savedGroup = groupRepository.save(group());
        long groupId = savedGroup.getId();

        Group foundGroup = groupRepository.findById(groupId).orElseThrow(() -> {
            throw new IllegalArgumentException("존재하지 않은 아이디입니다.");
        });

        assertThat(savedGroup.getName()).isEqualTo(foundGroup.getName());
        assertThat(savedGroup.getStandardDescription()).isEqualTo(foundGroup.getStandardDescription());
    }

    @Test
    @DisplayName("특정 아이디로 그룹을 조회 시 아이디가 옳바르지 않으면 예외를 발생한다.")
    void findByNotExistedGroupId() {
        long notExistedId = 9999999L;

        assertThrows(IllegalArgumentException.class, () -> {
            groupRepository.findById(notExistedId).orElseThrow(() -> {
                throw new IllegalArgumentException("존재하지 않은 아이디입니다.");
            });
        });
    }
    @Test
    @DisplayName("그룹들의 목록을 조회한다.")
    void findAllGroups() {
        groupRepository.save(group());

        List<Group> groups = groupRepository.findAll();

        assertThat(groups).hasSize(1);
    }
}