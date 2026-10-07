package com.projectproof.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectproof.entity.GithubActivity;
import com.projectproof.entity.GithubActivityType;
import com.projectproof.entity.GithubRepository;
import com.projectproof.entity.User;

public interface GithubActivityRepository extends JpaRepository<GithubActivity, Long> {

    List<GithubActivity> findByRepository(GithubRepository repository);

    List<GithubActivity> findByUser(User user);

    List<GithubActivity> findByActivityType(GithubActivityType activityType);

    List<GithubActivity> findByRepositoryAndActivityType(
            GithubRepository repository,
            GithubActivityType activityType
    );
}