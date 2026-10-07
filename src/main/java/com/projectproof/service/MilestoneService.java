package com.projectproof.service;

import com.projectproof.dto.milestone.CreateMilestoneRequest;
import com.projectproof.dto.milestone.MilestoneResponse;

import java.util.List;

public interface MilestoneService {

    MilestoneResponse createMilestone(
            Long projectId,
            CreateMilestoneRequest request,
            String userEmail);

    List<MilestoneResponse> getProjectMilestones(Long projectId);

    MilestoneResponse getMilestoneById(Long milestoneId);

    MilestoneResponse updateMilestone(
            Long milestoneId,
            CreateMilestoneRequest request,
            String userEmail);

    void deleteMilestone(
            Long milestoneId,
            String userEmail);
}