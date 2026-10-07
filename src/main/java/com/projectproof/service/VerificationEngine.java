package com.projectproof.service;

import java.math.BigDecimal;

public interface VerificationEngine {

    BigDecimal calculateMilestoneScore(Long projectId);

    BigDecimal calculateEvidenceScore(Long projectId);

    BigDecimal calculateGithubScore(Long projectId);

    BigDecimal calculateMentorScore(Long projectId);

    BigDecimal calculateTimelineScore(Long projectId);

    BigDecimal calculateFinalScore(
            BigDecimal milestoneScore,
            BigDecimal evidenceScore,
            BigDecimal githubScore,
            BigDecimal mentorScore,
            BigDecimal timelineScore);
}