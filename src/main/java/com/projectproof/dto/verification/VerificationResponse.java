package com.projectproof.dto.verification;

import com.projectproof.entity.VerificationStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class VerificationResponse {

    private Long id;
    private Long projectId;
    private String verificationId;

    private BigDecimal milestoneScore;
    private BigDecimal evidenceScore;
    private BigDecimal githubScore;
    private BigDecimal mentorScore;
    private BigDecimal timelineScore;
    private BigDecimal finalScore;

    private VerificationStatus status;

    private LocalDateTime verifiedAt;

    private String reportUrl;

    public VerificationResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getVerificationId() {
        return verificationId;
    }

    public void setVerificationId(String verificationId) {
        this.verificationId = verificationId;
    }

    public BigDecimal getMilestoneScore() {
        return milestoneScore;
    }

    public void setMilestoneScore(BigDecimal milestoneScore) {
        this.milestoneScore = milestoneScore;
    }

    public BigDecimal getEvidenceScore() {
        return evidenceScore;
    }

    public void setEvidenceScore(BigDecimal evidenceScore) {
        this.evidenceScore = evidenceScore;
    }

    public BigDecimal getGithubScore() {
        return githubScore;
    }

    public void setGithubScore(BigDecimal githubScore) {
        this.githubScore = githubScore;
    }

    public BigDecimal getMentorScore() {
        return mentorScore;
    }

    public void setMentorScore(BigDecimal mentorScore) {
        this.mentorScore = mentorScore;
    }

    public BigDecimal getTimelineScore() {
        return timelineScore;
    }

    public void setTimelineScore(BigDecimal timelineScore) {
        this.timelineScore = timelineScore;
    }

    public BigDecimal getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(BigDecimal finalScore) {
        this.finalScore = finalScore;
    }

    public VerificationStatus getStatus() {
        return status;
    }

    public void setStatus(VerificationStatus status) {
        this.status = status;
    }

    public LocalDateTime getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(LocalDateTime verifiedAt) {
        this.verifiedAt = verifiedAt;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }
}