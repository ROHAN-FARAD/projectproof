package com.projectproof.serviceimpl;

import com.projectproof.entity.GithubActivity;
import com.projectproof.entity.GithubActivityType;
import com.projectproof.entity.GithubRepository;
import com.projectproof.entity.User;
import com.projectproof.exception.ResourceNotFoundException;
import com.projectproof.repository.GithubActivityRepository;
import com.projectproof.repository.GithubRepositoryRepository;
import com.projectproof.repository.UserRepository;
import com.projectproof.service.GithubActivityService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class GithubActivityServiceImpl
        implements GithubActivityService {

    private final GithubActivityRepository githubActivityRepository;
    private final GithubRepositoryRepository githubRepositoryRepository;
    private final UserRepository userRepository;

    public GithubActivityServiceImpl(
            GithubActivityRepository githubActivityRepository,
            GithubRepositoryRepository githubRepositoryRepository,
            UserRepository userRepository) {

        this.githubActivityRepository = githubActivityRepository;
        this.githubRepositoryRepository = githubRepositoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public GithubActivity createActivity(
            Long repositoryId,
            Long userId,
            GithubActivityType activityType,
            String externalId,
            String activityUrl,
            LocalDateTime activityDate,
            String metadata) {

        GithubRepository repository =
                githubRepositoryRepository.findById(repositoryId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "GitHub repository not found with id: "
                                                + repositoryId));

        User user =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found with id: "
                                                + userId));

        GithubActivity activity = new GithubActivity();

        activity.setRepository(repository);
        activity.setUser(user);
        activity.setActivityType(activityType);
        activity.setExternalId(externalId);
        activity.setActivityUrl(activityUrl);
        activity.setActivityDate(
                activityDate != null
                        ? activityDate
                        : LocalDateTime.now()
        );
        activity.setMetadata(metadata);

        return githubActivityRepository.save(activity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GithubActivity> getActivitiesByRepository(
            Long repositoryId) {

        GithubRepository repository =
                githubRepositoryRepository.findById(repositoryId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "GitHub repository not found with id: "
                                                + repositoryId));

        return githubActivityRepository.findByRepository(repository);
    }

    @Override
    @Transactional(readOnly = true)
    public GithubActivity getActivityById(Long activityId) {

        return githubActivityRepository.findById(activityId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "GitHub activity not found with id: "
                                        + activityId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GithubActivity> getActivitiesByUser(Long userId) {

        User user =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found with id: "
                                                + userId));

        return githubActivityRepository.findByUser(user);
    }

    @Override
    public void deleteActivity(Long activityId) {

        GithubActivity activity =
                githubActivityRepository.findById(activityId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "GitHub activity not found with id: "
                                                + activityId));

        githubActivityRepository.delete(activity);
    }
}