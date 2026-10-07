package com.projectproof.serviceimpl;

import com.projectproof.dto.verification.VerificationResponse;
import com.projectproof.entity.Project;
import com.projectproof.entity.VerificationReport;
import com.projectproof.entity.VerificationStatus;
import com.projectproof.exception.ResourceNotFoundException;
import com.projectproof.repository.ProjectRepository;
import com.projectproof.repository.VerificationReportRepository;
import com.projectproof.service.VerificationEngine;
import com.projectproof.service.VerificationService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class VerificationServiceImpl implements VerificationService {

    private final ProjectRepository projectRepository;
    private final VerificationReportRepository verificationReportRepository;
    private final VerificationEngine verificationEngine;

    public VerificationServiceImpl(
            ProjectRepository projectRepository,
            VerificationReportRepository verificationReportRepository,
            VerificationEngine verificationEngine) {

        this.projectRepository = projectRepository;
        this.verificationReportRepository = verificationReportRepository;
        this.verificationEngine = verificationEngine;
    }

    @Override
    public VerificationResponse generateVerificationReport(Long projectId) {

        // 1. Find project
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with id: " + projectId));

        // 2. Calculate individual scores
        BigDecimal milestoneScore =
                verificationEngine.calculateMilestoneScore(projectId);

        BigDecimal evidenceScore =
                verificationEngine.calculateEvidenceScore(projectId);

        BigDecimal githubScore =
                verificationEngine.calculateGithubScore(projectId);

        BigDecimal mentorScore =
                verificationEngine.calculateMentorScore(projectId);

        BigDecimal timelineScore =
                verificationEngine.calculateTimelineScore(projectId);

        // 3. Calculate final score
        BigDecimal finalScore =
                verificationEngine.calculateFinalScore(
                        milestoneScore,
                        evidenceScore,
                        githubScore,
                        mentorScore,
                        timelineScore);

        // 4. Determine verification status
        VerificationStatus status = determineStatus(finalScore);

        // 5. Check whether report already exists
        VerificationReport report =
                verificationReportRepository.findByProject(project)
                        .orElseGet(VerificationReport::new);

        // 6. Set project
        report.setProject(project);

        // 7. Generate verification ID only for new report
        if (report.getVerificationId() == null) {
            report.setVerificationId(generateVerificationId());
        }

        // 8. Store scores
        report.setMilestoneScore(milestoneScore);
        report.setEvidenceScore(evidenceScore);
        report.setGithubScore(githubScore);
        report.setMentorScore(mentorScore);
        report.setTimelineScore(timelineScore);
        report.setFinalScore(finalScore);

        // 9. Set status and verification time
        report.setStatus(status);
        report.setVerifiedAt(LocalDateTime.now());

        // 10. Save report
        VerificationReport savedReport =
                verificationReportRepository.save(report);

        // 11. Convert entity to response DTO
        return mapToResponse(savedReport);
    }

    @Override
    @Transactional(readOnly = true)
    public VerificationResponse getVerificationReport(Long projectId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with id: " + projectId));

        VerificationReport report =
                verificationReportRepository.findByProject(project)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Verification report not found for project id: "
                                                + projectId));

        return mapToResponse(report);
    }

    @Override
    @Transactional(readOnly = true)
    public VerificationResponse getVerificationById(Long verificationId) {

        VerificationReport report =
                verificationReportRepository.findById(verificationId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Verification report not found with id: "
                                                + verificationId));

        return mapToResponse(report);
    }

    private String generateVerificationId() {

        return "PP-" +
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();
    }

    private VerificationStatus determineStatus(BigDecimal finalScore) {

        if (finalScore.compareTo(BigDecimal.valueOf(80)) >= 0) {
            return VerificationStatus.HIGHLY_VERIFIED;
        }

        if (finalScore.compareTo(BigDecimal.valueOf(60)) >= 0) {
            return VerificationStatus.VERIFIED;
        }

        if (finalScore.compareTo(BigDecimal.valueOf(40)) >= 0) {
            return VerificationStatus.PARTIALLY_VERIFIED;
        }

        return VerificationStatus.INSUFFICIENT_EVIDENCE;
    }

    private VerificationResponse mapToResponse(
            VerificationReport report) {

        VerificationResponse response = new VerificationResponse();

        response.setId(report.getId());

        if (report.getProject() != null) {
            response.setProjectId(report.getProject().getId());
        }

        response.setVerificationId(report.getVerificationId());
        response.setMilestoneScore(report.getMilestoneScore());
        response.setEvidenceScore(report.getEvidenceScore());
        response.setGithubScore(report.getGithubScore());
        response.setMentorScore(report.getMentorScore());
        response.setTimelineScore(report.getTimelineScore());
        response.setFinalScore(report.getFinalScore());
        response.setStatus(report.getStatus());
        response.setVerifiedAt(report.getVerifiedAt());
        response.setReportUrl(report.getReportUrl());

        return response;
    }
}