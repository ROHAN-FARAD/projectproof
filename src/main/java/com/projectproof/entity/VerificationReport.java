package com.projectproof.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "verification_reports")
public class VerificationReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false, unique = true)
    private Project project;

    @Column(name = "verification_id", nullable = false, unique = true, length = 50)
    private String verificationId;

    @Column(name = "milestone_score", precision = 5, scale = 2)
    private BigDecimal milestoneScore;

    @Column(name = "evidence_score", precision = 5, scale = 2)
    private BigDecimal evidenceScore;

    @Column(name = "github_score", precision = 5, scale = 2)
    private BigDecimal githubScore;

    @Column(name = "mentor_score", precision = 5, scale = 2)
    private BigDecimal mentorScore;

    @Column(name = "timeline_score", precision = 5, scale = 2)
    private BigDecimal timelineScore;

    @Column(name = "final_score", precision = 5, scale = 2)
    private BigDecimal finalScore;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private VerificationStatus status;

    @Column(name = "verified_at")
    private LocalDateTime verifiedAt;

    @Column(name = "report_url", length = 500)
    private String reportUrl;

    public VerificationReport() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
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