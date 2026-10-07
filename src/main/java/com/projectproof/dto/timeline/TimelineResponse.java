package com.projectproof.dto.timeline;

import com.projectproof.entity.TimelineEventType;

import java.time.LocalDateTime;

public class TimelineResponse {

    private Long id;

    private Long projectId;
    private Long userId;

    private String userName;

    private TimelineEventType eventType;

    private String title;
    private String description;

    private Long referenceId;

    private LocalDateTime createdAt;


    public TimelineResponse() {
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


    public TimelineEventType getEventType() {
        return eventType;
    }

    public void setEventType(TimelineEventType eventType) {
        this.eventType = eventType;
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


    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}