package com.projectproof.dto.evidence;

import jakarta.validation.constraints.NotBlank;

public class EvidenceRequest {

    @NotBlank(message = "Evidence type is required")
    private String type;

    @NotBlank(message = "Evidence title is required")
    private String title;

    private String description;

    private String fileUrl;

    private String externalUrl;

    public EvidenceRequest() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getExternalUrl() {
        return externalUrl;
    }

    public void setExternalUrl(String externalUrl) {
        this.externalUrl = externalUrl;
    }
}