package com.projectproof.service;

import com.projectproof.entity.GithubRepository;

import java.util.List;

public interface GithubRepositoryService {

    GithubRepository connectRepository(
            Long projectId,
            String repositoryUrl,
            String repositoryName,
            String ownerName,
            Boolean isPrivate
    );

    GithubRepository getRepositoryByProject(Long projectId);

    GithubRepository getRepositoryById(Long repositoryId);

    List<GithubRepository> getAllRepositories();

    GithubRepository updateRepository(
            Long repositoryId,
            String repositoryUrl,
            String repositoryName,
            String ownerName,
            Boolean isPrivate
    );

    void deleteRepository(Long repositoryId);
}