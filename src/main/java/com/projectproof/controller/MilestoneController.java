package com.projectproof.controller;

import com.projectproof.dto.milestone.CreateMilestoneRequest;
import com.projectproof.dto.milestone.MilestoneResponse;
import com.projectproof.service.MilestoneService;

import jakarta.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class MilestoneController {

    private final MilestoneService milestoneService;

    public MilestoneController(MilestoneService milestoneService) {
        this.milestoneService = milestoneService;
    }

    // Create milestone
    @PostMapping("/projects/{projectId}/milestones")
    public MilestoneResponse createMilestone(
            @PathVariable Long projectId,
            @Valid @RequestBody CreateMilestoneRequest request,
            Authentication authentication) {

        return milestoneService.createMilestone(
                projectId,
                request,
                authentication.getName());
    }

    // Get all milestones of a project
    @GetMapping("/projects/{projectId}/milestones")
    public List<MilestoneResponse> getProjectMilestones(
            @PathVariable Long projectId) {

        return milestoneService.getProjectMilestones(projectId);
    }

    // Get milestone by ID
    @GetMapping("/milestones/{milestoneId}")
    public MilestoneResponse getMilestoneById(
            @PathVariable Long milestoneId) {

        return milestoneService.getMilestoneById(milestoneId);
    }

    // Update milestone
    @PutMapping("/milestones/{milestoneId}")
    public MilestoneResponse updateMilestone(
            @PathVariable Long milestoneId,
            @Valid @RequestBody CreateMilestoneRequest request,
            Authentication authentication) {

        return milestoneService.updateMilestone(
                milestoneId,
                request,
                authentication.getName());
    }

    // Delete milestone
    @DeleteMapping("/milestones/{milestoneId}")
    public String deleteMilestone(
            @PathVariable Long milestoneId,
            Authentication authentication) {

        milestoneService.deleteMilestone(
                milestoneId,
                authentication.getName());

        return "Milestone deleted successfully";
    }
}