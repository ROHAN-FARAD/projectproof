package com.projectproof.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectproof.entity.GithubRepository;
import com.projectproof.entity.Project;

public interface GithubRepositoryRepository extends JpaRepository<GithubRepository, Long> {

    Optional<GithubRepository> findByProject(Project project);

    boolean existsByProject(Project project);

    Optional<GithubRepository> findByRepositoryUrl(String repositoryUrl);
}