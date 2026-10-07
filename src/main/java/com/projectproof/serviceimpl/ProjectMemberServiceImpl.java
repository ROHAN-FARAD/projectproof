package com.projectproof.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projectproof.dto.projectmember.ProjectMemberRequest;
import com.projectproof.dto.projectmember.ProjectMemberResponse;
import com.projectproof.entity.Project;
import com.projectproof.entity.ProjectMember;
import com.projectproof.entity.User;
import com.projectproof.exception.BadRequestException;
import com.projectproof.exception.ResourceNotFoundException;
import com.projectproof.repository.ProjectMemberRepository;
import com.projectproof.repository.ProjectRepository;
import com.projectproof.repository.UserRepository;
import com.projectproof.service.ProjectMemberService;

@Service
@Transactional
public class ProjectMemberServiceImpl implements ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectMemberServiceImpl(
            ProjectMemberRepository projectMemberRepository,
            ProjectRepository projectRepository,
            UserRepository userRepository) {

        this.projectMemberRepository = projectMemberRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ProjectMemberResponse addMember(
            Long projectId,
            ProjectMemberRequest request,
            String userEmail) {

        // Find logged-in user
        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with email: " + userEmail));

        // Find project
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with id: " + projectId));

        // Only project creator can add members
        if (!project.getCreatedBy().getId().equals(currentUser.getId())) {
            throw new BadRequestException(
                    "Only the project creator can add members");
        }

        // Find user to add
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + request.getUserId()));

        // Check duplicate member
        if (projectMemberRepository.existsByProjectAndUser(project, user)) {
            throw new BadRequestException(
                    "User is already a member of this project");
        }

        // Create project member
        ProjectMember member = new ProjectMember();

        member.setProject(project);
        member.setUser(user);
        member.setMemberRole(request.getMemberRole());
        member.setJoinedAt(LocalDateTime.now());

        ProjectMember savedMember =
                projectMemberRepository.save(member);

        return mapToResponse(savedMember);
    }

    @Override
    public List<ProjectMemberResponse> getProjectMembers(Long projectId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with id: " + projectId));

        return projectMemberRepository.findByProject(project)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ProjectMemberResponse getMemberById(Long memberId) {

        ProjectMember member = projectMemberRepository.findById(memberId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project member not found with id: " + memberId));

        return mapToResponse(member);
    }

    @Override
    public void removeMember(
            Long memberId,
            String userEmail) {

        // Find logged-in user
        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with email: " + userEmail));

        // Find member
        ProjectMember member = projectMemberRepository.findById(memberId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project member not found with id: " + memberId));

        // Get project
        Project project = member.getProject();

        // Only project creator can remove members
        if (!project.getCreatedBy().getId().equals(currentUser.getId())) {
            throw new BadRequestException(
                    "Only the project creator can remove members");
        }

        projectMemberRepository.delete(member);
    }

    private ProjectMemberResponse mapToResponse(
            ProjectMember member) {

        ProjectMemberResponse response =
                new ProjectMemberResponse();

        response.setId(member.getId());

        if (member.getProject() != null) {
            response.setProjectId(
                    member.getProject().getId());

            response.setProjectTitle(
                    member.getProject().getTitle());
        }

        if (member.getUser() != null) {
            response.setUserId(
                    member.getUser().getId());

            response.setUserName(
                    member.getUser().getName());

            response.setUserEmail(
                    member.getUser().getEmail());
        }

        response.setMemberRole(member.getMemberRole());
        response.setJoinedAt(member.getJoinedAt());

        return response;
    }
}