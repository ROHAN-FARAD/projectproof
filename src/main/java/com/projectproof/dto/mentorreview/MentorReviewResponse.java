package com.projectproof.dto.mentorreview;

import com.projectproof.entity.MentorReviewStatus;

import java.time.LocalDateTime;

public class MentorReviewResponse {

    private Long id;

    private Long milestoneId;
    private String milestoneTitle;

    private Long mentorId;
    private String mentorName;
    private String mentorEmail;

    private MentorReviewStatus status;

    private String comments;

    private LocalDateTime reviewedAt;


    public MentorReviewResponse() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(Long milestoneId) {
        this.milestoneId = milestoneId;
    }


    public String getMilestoneTitle() {
        return milestoneTitle;
    }

    public void setMilestoneTitle(String milestoneTitle) {
        this.milestoneTitle = milestoneTitle;
    }


    public Long getMentorId() {
        return mentorId;
    }

    public void setMentorId(Long mentorId) {
        this.mentorId = mentorId;
    }


    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }


    public String getMentorEmail() {
        return mentorEmail;
    }

    public void setMentorEmail(String mentorEmail) {
        this.mentorEmail = mentorEmail;
    }


    public MentorReviewStatus getStatus() {
        return status;
    }

    public void setStatus(MentorReviewStatus status) {
        this.status = status;
    }


    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


    public LocalDateTime getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(LocalDateTime reviewedAt) {
        this.reviewedAt = reviewedAt;
    }
}