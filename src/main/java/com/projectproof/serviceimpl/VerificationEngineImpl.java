package com.projectproof.serviceimpl;

import com.projectproof.entity.GithubActivity;
import com.projectproof.entity.GithubRepository;
import com.projectproof.entity.MentorReview;
import com.projectproof.entity.MentorReviewStatus;
import com.projectproof.entity.Milestone;
import com.projectproof.entity.MilestoneStatus;
import com.projectproof.entity.Project;
import com.projectproof.entity.ProjectTimeline;

import com.projectproof.exception.ResourceNotFoundException;

import com.projectproof.repository.EvidenceRepository;
import com.projectproof.repository.GithubActivityRepository;
import com.projectproof.repository.GithubRepositoryRepository;
import com.projectproof.repository.MentorReviewRepository;
import com.projectproof.repository.MilestoneRepository;
import com.projectproof.repository.ProjectRepository;
import com.projectproof.repository.ProjectTimelineRepository;

import com.projectproof.service.VerificationEngine;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VerificationEngineImpl implements VerificationEngine {

    private final ProjectRepository projectRepository;
    private final MilestoneRepository milestoneRepository;
    private final EvidenceRepository evidenceRepository;
    private final GithubRepositoryRepository githubRepositoryRepository;
    private final GithubActivityRepository githubActivityRepository;
    private final MentorReviewRepository mentorReviewRepository;
    private final ProjectTimelineRepository projectTimelineRepository;

    public VerificationEngineImpl(
            ProjectRepository projectRepository,
            MilestoneRepository milestoneRepository,
            EvidenceRepository evidenceRepository,
            GithubRepositoryRepository githubRepositoryRepository,
            GithubActivityRepository githubActivityRepository,
            MentorReviewRepository mentorReviewRepository,
            ProjectTimelineRepository projectTimelineRepository) {

        this.projectRepository = projectRepository;
        this.milestoneRepository = milestoneRepository;
        this.evidenceRepository = evidenceRepository;
        this.githubRepositoryRepository = githubRepositoryRepository;
        this.githubActivityRepository = githubActivityRepository;
        this.mentorReviewRepository = mentorReviewRepository;
        this.projectTimelineRepository = projectTimelineRepository;
    }

    @Override
    public BigDecimal calculateMilestoneScore(Long projectId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with id: " + projectId));

        List<Milestone> milestones =
                milestoneRepository.findByProject(project);

        if (milestones.isEmpty()) {
            return BigDecimal.ZERO;
        }

        long approvedMilestones = milestones.stream()
                .filter(milestone ->
                        milestone.getStatus() == MilestoneStatus.APPROVED)
                .count();

        return BigDecimal.valueOf(approvedMilestones)
                .divide(
                        BigDecimal.valueOf(milestones.size()),
                        2,
                        RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    @Override
    public BigDecimal calculateEvidenceScore(Long projectId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with id: " + projectId));

        List<Milestone> milestones =
                milestoneRepository.findByProject(project);

        if (milestones.isEmpty()) {
            return BigDecimal.ZERO;
        }

        long milestonesWithEvidence = milestones.stream()
                .filter(milestone ->
                        !evidenceRepository
                                .findByMilestone(milestone)
                                .isEmpty())
                .count();

        return BigDecimal.valueOf(milestonesWithEvidence)
                .divide(
                        BigDecimal.valueOf(milestones.size()),
                        2,
                        RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    @Override
    public BigDecimal calculateGithubScore(Long projectId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with id: " + projectId));

        Optional<GithubRepository> githubRepository =
                githubRepositoryRepository.findByProject(project);

        if (githubRepository.isEmpty()) {
            return BigDecimal.ZERO;
        }

        List<GithubActivity> activities =
                githubActivityRepository.findByRepository(
                        githubRepository.get());

        int activityCount = activities.size();

        if (activityCount == 0) {
            return BigDecimal.ZERO;
        }

        if (activityCount < 5) {
            return BigDecimal.valueOf(25);
        }

        if (activityCount < 10) {
            return BigDecimal.valueOf(50);
        }

        if (activityCount < 20) {
            return BigDecimal.valueOf(75);
        }

        return BigDecimal.valueOf(100);
    }

    @Override
    public BigDecimal calculateMentorScore(Long projectId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with id: " + projectId));

        List<Milestone> milestones =
                milestoneRepository.findByProject(project);

        if (milestones.isEmpty()) {
            return BigDecimal.ZERO;
        }

        int totalReviews = 0;
        int approvedReviews = 0;

        for (Milestone milestone : milestones) {

            List<MentorReview> reviews =
                    mentorReviewRepository.findByMilestone(milestone);

            totalReviews += reviews.size();

            approvedReviews += reviews.stream()
                    .filter(review ->
                            review.getStatus() ==
                                    MentorReviewStatus.APPROVED)
                    .count();
        }

        if (totalReviews == 0) {
            return BigDecimal.ZERO;
        }

        return BigDecimal.valueOf(approvedReviews)
                .divide(
                        BigDecimal.valueOf(totalReviews),
                        2,
                        RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    @Override
    public BigDecimal calculateTimelineScore(Long projectId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with id: " + projectId));

        List<ProjectTimeline> timelineEvents =
                projectTimelineRepository
                        .findByProjectOrderByCreatedAtAsc(project);

        int eventCount = timelineEvents.size();

        if (eventCount == 0) {
            return BigDecimal.ZERO;
        }

        if (eventCount <= 2) {
            return BigDecimal.valueOf(25);
        }

        if (eventCount <= 4) {
            return BigDecimal.valueOf(50);
        }

        if (eventCount <= 9) {
            return BigDecimal.valueOf(75);
        }

        return BigDecimal.valueOf(100);
    }

    @Override
public BigDecimal calculateFinalScore(
        BigDecimal milestoneScore,
        BigDecimal evidenceScore,
        BigDecimal githubScore,
        BigDecimal mentorScore,
        BigDecimal timelineScore) {

    BigDecimal milestoneWeight = BigDecimal.valueOf(0.25);
    BigDecimal evidenceWeight = BigDecimal.valueOf(0.20);
    BigDecimal githubWeight = BigDecimal.valueOf(0.20);
    BigDecimal mentorWeight = BigDecimal.valueOf(0.20);
    BigDecimal timelineWeight = BigDecimal.valueOf(0.15);

    BigDecimal finalScore =
            milestoneScore.multiply(milestoneWeight)
                    .add(evidenceScore.multiply(evidenceWeight))
                    .add(githubScore.multiply(githubWeight))
                    .add(mentorScore.multiply(mentorWeight))
                    .add(timelineScore.multiply(timelineWeight));

    return finalScore.setScale(2, RoundingMode.HALF_UP);
}
}