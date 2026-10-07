package com.projectproof.controller;

import com.projectproof.service.GithubApiService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/github")
public class GithubSyncController {

    private final GithubApiService githubApiService;

    public GithubSyncController(GithubApiService githubApiService) {
        this.githubApiService = githubApiService;
    }

    @GetMapping("/commits/{owner}/{repositoryName}")
    public List<Map<String, Object>> getCommits(
            @PathVariable String owner,
            @PathVariable String repositoryName) {

        return githubApiService.getRepositoryCommits(
                owner,
                repositoryName
        );
    }
}