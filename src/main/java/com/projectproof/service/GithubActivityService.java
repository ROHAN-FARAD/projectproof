package com.projectproof.service;

import com.projectproof.entity.GithubActivity;
import com.projectproof.entity.GithubActivityType;

import java.time.LocalDateTime;
import java.util.List;

public interface GithubActivityService {

    GithubActivity createActivity(
            Long repositoryId,
            Long userId,
            GithubActivityType activityType,
            String externalId,
            String activityUrl,
            LocalDateTime activityDate,
            String metadata
    );

    List<GithubActivity> getActivitiesByRepository(Long repositoryId);

    GithubActivity getActivityById(Long activityId);

    List<GithubActivity> getActivitiesByUser(Long userId);

    void deleteActivity(Long activityId);
}