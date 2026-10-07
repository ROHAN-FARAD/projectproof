package com.projectproof.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectproof.dto.projectmember.ProjectMemberRequest;
import com.projectproof.dto.projectmember.ProjectMemberResponse;
import com.projectproof.service.ProjectMemberService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;

    public ProjectMemberController(
            ProjectMemberService projectMemberService) {
        this.projectMemberService = projectMemberService;
    }

    // Add member to project
    @PostMapping("/projects/{projectId}/members")
    public ProjectMemberResponse addMember(
            @PathVariable Long projectId,
            @Valid @RequestBody ProjectMemberRequest request,
            Authentication authentication) {

        return projectMemberService.addMember(
                projectId,
                request,
                authentication.getName());
    }

    // Get all members of a project
    @GetMapping("/projects/{projectId}/members")
    public List<ProjectMemberResponse> getProjectMembers(
            @PathVariable Long projectId) {

        return projectMemberService.getProjectMembers(projectId);
    }

    // Get member by ID
    @GetMapping("/project-members/{memberId}")
    public ProjectMemberResponse getMemberById(
            @PathVariable Long memberId) {

        return projectMemberService.getMemberById(memberId);
    }

    // Remove member
    @DeleteMapping("/project-members/{memberId}")
    public String removeMember(
            @PathVariable Long memberId,
            Authentication authentication) {

        projectMemberService.removeMember(
                memberId,
                authentication.getName());

        return "Project member removed successfully";
    }
}