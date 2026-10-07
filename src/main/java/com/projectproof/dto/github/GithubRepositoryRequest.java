package com.projectproof.dto.github;

import jakarta.validation.constraints.NotBlank;

public class GithubRepositoryRequest {

    @NotBlank(message = "Repository URL is required")
    private String repositoryUrl;

    @NotBlank(message = "Repository name is required")
    private String repositoryName;

    @NotBlank(message = "Owner name is required")
    private String ownerName;

    private Boolean isPrivate = false;

    public GithubRepositoryRequest() {
    }

    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    public void setRepositoryUrl(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }
}