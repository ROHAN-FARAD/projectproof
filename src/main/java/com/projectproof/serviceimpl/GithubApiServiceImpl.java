package com.projectproof.serviceimpl;

import com.projectproof.service.GithubApiService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class GithubApiServiceImpl implements GithubApiService {

    private final WebClient githubWebClient;

    public GithubApiServiceImpl(WebClient githubWebClient) {
        this.githubWebClient = githubWebClient;
    }

    @Override
    public List<Map<String, Object>> getRepositoryCommits(
            String owner,
            String repositoryName) {

        return githubWebClient.get()
                .uri("/repos/{owner}/{repo}/commits", owner, repositoryName)
                .retrieve()
                .bodyToFlux(Map.class)
                .map(commit -> (Map<String, Object>) commit)
                .collectList()
                .block();
    }
}