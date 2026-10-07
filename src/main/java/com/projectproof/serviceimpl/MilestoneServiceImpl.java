package com.projectproof.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projectproof.dto.milestone.CreateMilestoneRequest;
import com.projectproof.dto.milestone.MilestoneResponse;
import com.projectproof.entity.Milestone;
import com.projectproof.entity.MilestoneStatus;
import com.projectproof.entity.Project;
import com.projectproof.entity.User;
import com.projectproof.exception.BadRequestException;
import com.projectproof.exception.ResourceNotFoundException;
import com.projectproof.repository.MilestoneRepository;
import com.projectproof.repository.ProjectRepository;
import com.projectproof.repository.UserRepository;
import com.projectproof.service.MilestoneService;

@Service
@Transactional
public class MilestoneServiceImpl implements MilestoneService {

    private final MilestoneRepository milestoneRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public MilestoneServiceImpl(
            MilestoneRepository milestoneRepository,
            ProjectRepository projectRepository,
            UserRepository userRepository) {

        this.milestoneRepository = milestoneRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public MilestoneResponse createMilestone(
            Long projectId,
            CreateMilestoneRequest request,
            String userEmail) {

        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with email: " + userEmail));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with id: " + projectId));

        // Only project creator can create milestones
        if (!project.getCreatedBy().getId().equals(currentUser.getId())) {
            throw new BadRequestException(
                    "Only the project creator can create milestones");
        }

        Milestone milestone = new Milestone();

        milestone.setProject(project);
        milestone.setTitle(request.getTitle());
        milestone.setDescription(request.getDescription());
        milestone.setStartDate(request.getStartDate());
        milestone.setDeadline(request.getDeadline());
        milestone.setStatus(MilestoneStatus.PENDING);
        milestone.setCreatedAt(LocalDateTime.now());
        milestone.setUpdatedAt(LocalDateTime.now());

        Milestone savedMilestone =
                milestoneRepository.save(milestone);

        return mapToResponse(savedMilestone);
    }

    @Override
    public List<MilestoneResponse> getProjectMilestones(
            Long projectId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with id: " + projectId));

        return milestoneRepository.findByProject(project)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public MilestoneResponse getMilestoneById(
            Long milestoneId) {

        Milestone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Milestone not found with id: " + milestoneId));

        return mapToResponse(milestone);
    }

    @Override
    public MilestoneResponse updateMilestone(
            Long milestoneId,
            CreateMilestoneRequest request,
            String userEmail) {

        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with email: " + userEmail));

        Milestone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Milestone not found with id: " + milestoneId));

        Project project = milestone.getProject();

        // Only project creator can update milestones
        if (!project.getCreatedBy().getId().equals(currentUser.getId())) {
            throw new BadRequestException(
                    "Only the project creator can update milestones");
        }

        milestone.setTitle(request.getTitle());
        milestone.setDescription(request.getDescription());
        milestone.setStartDate(request.getStartDate());
        milestone.setDeadline(request.getDeadline());
        milestone.setUpdatedAt(LocalDateTime.now());

        Milestone updatedMilestone =
                milestoneRepository.save(milestone);

        return mapToResponse(updatedMilestone);
    }

    @Override
    public void deleteMilestone(
            Long milestoneId,
            String userEmail) {

        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with email: " + userEmail));

        Milestone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Milestone not found with id: " + milestoneId));

        Project project = milestone.getProject();

        // Only project creator can delete milestones
        if (!project.getCreatedBy().getId().equals(currentUser.getId())) {
            throw new BadRequestException(
                    "Only the project creator can delete milestones");
        }

        milestoneRepository.delete(milestone);
    }

    private MilestoneResponse mapToResponse(
            Milestone milestone) {

        MilestoneResponse response =
                new MilestoneResponse();

        response.setId(milestone.getId());

        if (milestone.getProject() != null) {
            response.setProjectId(
                    milestone.getProject().getId());

            response.setProjectTitle(
                    milestone.getProject().getTitle());
        }

        response.setTitle(milestone.getTitle());
        response.setDescription(milestone.getDescription());
        response.setStartDate(milestone.getStartDate());
        response.setDeadline(milestone.getDeadline());
        response.setSubmittedAt(milestone.getSubmittedAt());
        response.setReviewedAt(milestone.getReviewedAt());
        response.setStatus(milestone.getStatus());
        response.setCreatedAt(milestone.getCreatedAt());
        response.setUpdatedAt(milestone.getUpdatedAt());

        return response;
    }
}