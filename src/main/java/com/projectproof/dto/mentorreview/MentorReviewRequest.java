package com.projectproof.dto.mentorreview;

import jakarta.validation.constraints.NotBlank;

public class MentorReviewRequest {

    @NotBlank(message = "Review status is required")
    private String status;

    private String comments;

    public MentorReviewRequest() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}