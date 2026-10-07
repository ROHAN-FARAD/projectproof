package com.projectproof.dto.github;

import com.projectproof.entity.GithubActivityType;

import java.time.LocalDateTime;

public class GithubActivityResponse {

    private Long id;
    private Long repositoryId;
    private Long userId;
    private String userName;
    private GithubActivityType activityType;
    private String externalId;
    private String activityUrl;
    private LocalDateTime activityDate;
    private String metadata;

    public GithubActivityResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(Long repositoryId) {
        this.repositoryId = repositoryId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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