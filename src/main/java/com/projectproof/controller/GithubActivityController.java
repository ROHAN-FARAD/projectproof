package com.projectproof.controller;

import com.projectproof.dto.github.GithubActivityRequest;
import com.projectproof.dto.github.GithubActivityResponse;
import com.projectproof.entity.GithubActivity;
import com.projectproof.entity.GithubActivityType;
import com.projectproof.service.GithubActivityService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class GithubActivityController {

    private final GithubActivityService githubActivityService;

    public GithubActivityController(
            GithubActivityService githubActivityService) {
        this.githubActivityService = githubActivityService;
    }

    // Create GitHub activity
    @PostMapping("/github/{repositoryId}/activities")
    @ResponseStatus(HttpStatus.CREATED)
    public GithubActivityResponse createActivity(
            @PathVariable Long repositoryId,
            @Valid @RequestBody GithubActivityRequest request) {

        GithubActivity activity =
                githubActivityService.createActivity(
                        repositoryId,
                        request.getUserId(),
                        request.getActivityType(),
                        request.getExternalId(),
                        request.getActivityUrl(),
                        request.getActivityDate(),
                        request.getMetadata()
                );

        return mapToResponse(activity);
    }

    // Get all activities of a repository
    @GetMapping("/github/{repositoryId}/activities")
    public List<GithubActivityResponse> getActivitiesByRepository(
            @PathVariable Long repositoryId) {

        return githubActivityService
                .getActivitiesByRepository(repositoryId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // Get activity by ID
    @GetMapping("/github/activities/{activityId}")
    public GithubActivityResponse getActivityById(
            @PathVariable Long activityId) {

        GithubActivity activity =
                githubActivityService.getActivityById(activityId);

        return mapToResponse(activity);
    }

    // Get activities by user
    @GetMapping("/github/activities/user/{userId}")
    public List<GithubActivityResponse> getActivitiesByUser(
            @PathVariable Long userId) {

        return githubActivityService
                .getActivitiesByUser(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // Delete activity
    @DeleteMapping("/github/activities/{activityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteActivity(
            @PathVariable Long activityId) {

        githubActivityService.deleteActivity(activityId);
    }

    // Entity → Response DTO
    private GithubActivityResponse mapToResponse(
            GithubActivity activity) {

        GithubActivityResponse response =
                new GithubActivityResponse();

        response.setId(activity.getId());

        if (activity.getRepository() != null) {
            response.setRepositoryId(
                    activity.getRepository().getId());
        }

        if (activity.getUser() != null) {
            response.setUserId(
                    activity.getUser().getId());

            response.setUserName(
                    activity.getUser().getName());
        }

        response.setActivityType(
                activity.getActivityType());

        response.setExternalId(
                activity.getExternalId());

        response.setActivityUrl(
                activity.getActivityUrl());

        response.setActivityDate(
                activity.getActivityDate());

        response.setMetadata(
                activity.getMetadata());

        return response;
    }
}