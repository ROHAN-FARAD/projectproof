package com.projectproof.dto.github;

import com.projectproof.entity.GithubActivityType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class GithubActivityRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Activity type is required")
    private GithubActivityType activityType;

    private String externalId;

    private String activityUrl;

    private LocalDateTime activityDate;

    private String metadata;

    public GithubActivityRequest() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public GithubActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(GithubActivityType activityType) {
        this.activityType = activityType;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getActivityUrl() {
        return activityUrl;
    }

    public void setActivityUrl(String activityUrl) {
        this.activityUrl = activityUrl;
    }

    public LocalDateTime getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(LocalDateTime activityDate) {
        this.activityDate = activityDate;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}