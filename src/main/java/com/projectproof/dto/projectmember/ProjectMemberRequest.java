package com.projectproof.dto.projectmember;

import jakarta.validation.constraints.NotNull;

public class ProjectMemberRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    private String memberRole;

    public ProjectMemberRequest() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMemberRole() {
        return memberRole;
    }

    public void setMemberRole(String memberRole) {
        this.memberRole = memberRole;
    }
}