package com.projectproof.controller;

import com.projectproof.dto.github.GithubRepositoryRequest;
import com.projectproof.dto.github.GithubRepositoryResponse;
import com.projectproof.entity.GithubRepository;
import com.projectproof.service.GithubRepositoryService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class GithubRepositoryController {

    private final GithubRepositoryService githubRepositoryService;

    public GithubRepositoryController(
            GithubRepositoryService githubRepositoryService) {
        this.githubRepositoryService = githubRepositoryService;
    }

    // Connect GitHub repository to a project
    @PostMapping("/projects/{projectId}/github")
    @ResponseStatus(HttpStatus.CREATED)
    public GithubRepositoryResponse connectRepository(
            @PathVariable Long projectId,
            @Valid @RequestBody GithubRepositoryRequest request) {

        GithubRepository repository =
                githubRepositoryService.connectRepository(
                        projectId,
                        request.getRepositoryUrl(),
                        request.getRepositoryName(),
                        request.getOwnerName(),
                        request.getIsPrivate()
                );

        return mapToResponse(repository);
    }

    // Get GitHub repository of a project
    @GetMapping("/projects/{projectId}/github")
    public GithubRepositoryResponse getRepositoryByProject(
            @PathVariable Long projectId) {

        GithubRepository repository =
                githubRepositoryService.getRepositoryByProject(projectId);

        return mapToResponse(repository);
    }

    // Get repository by ID
    @GetMapping("/github/{repositoryId}")
    public GithubRepositoryResponse getRepositoryById(
            @PathVariable Long repositoryId) {

        GithubRepository repository =
                githubRepositoryService.getRepositoryById(repositoryId);

        return mapToResponse(repository);
    }

    // Get all connected repositories
    @GetMapping("/github")
    public List<GithubRepositoryResponse> getAllRepositories() {

        return githubRepositoryService.getAllRepositories()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // Update repository
    @PutMapping("/github/{repositoryId}")
    public GithubRepositoryResponse updateRepository(
            @PathVariable Long repositoryId,
            @Valid @RequestBody GithubRepositoryRequest request) {

        GithubRepository repository =
                githubRepositoryService.updateRepository(
                        repositoryId,
                        request.getRepositoryUrl(),
                        request.getRepositoryName(),
                        request.getOwnerName(),
                        request.getIsPrivate()
                );

        return mapToResponse(repository);
    }

    // Delete repository
    @DeleteMapping("/github/{repositoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRepository(
            @PathVariable Long repositoryId) {

        githubRepositoryService.deleteRepository(repositoryId);
    }

    // Entity → Response DTO
    private GithubRepositoryResponse mapToResponse(
            GithubRepository repository) {

        GithubRepositoryResponse response =
                new GithubRepositoryResponse();

        response.setId(repository.getId());

        if (repository.getProject() != null) {
            response.setProjectId(
                    repository.getProject().getId());
        }

        response.setRepositoryUrl(
                repository.getRepositoryUrl());

        response.setRepositoryName(
                repository.getRepositoryName());

        response.setOwnerName(
                repository.getOwnerName());

        response.setConnectedAt(
                repository.getConnectedAt());

        response.setIsPrivate(
                repository.getIsPrivate());

        return response;
    }
}