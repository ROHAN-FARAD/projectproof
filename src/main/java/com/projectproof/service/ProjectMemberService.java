package com.projectproof.service;

import java.util.List;

import com.projectproof.dto.projectmember.ProjectMemberRequest;
import com.projectproof.dto.projectmember.ProjectMemberResponse;

public interface ProjectMemberService {

    ProjectMemberResponse addMember(
            Long projectId,
            ProjectMemberRequest request,
            String userEmail);

    List<ProjectMemberResponse> getProjectMembers(Long projectId);

    ProjectMemberResponse getMemberById(Long memberId);

    void removeMember(Long memberId, String userEmail);
}