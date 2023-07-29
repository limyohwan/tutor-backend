package com.yohwan.tutor.dto.response;

import com.yohwan.tutor.domain.entity.Group;
import com.yohwan.tutor.domain.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class GroupResponse {
    private Long id;
    private String name;
    private String standardDescription;
    private List<StudentResponse> students;

    public GroupResponse(Group group, List<Member> members) {
        this(
                group.getId(),
                group.getName(),
                group.getStandardDescription(),
                members.stream().map(member -> new StudentResponse(member.getStudent())).collect(Collectors.toList())
        );
    }

    public GroupResponse(Long id, String name, String standardDescription, List<StudentResponse> students) {
        this.id = id;
        this.name = name;
        this.standardDescription = standardDescription;
        this.students = students != null ? students : Collections.emptyList();
    }
}
