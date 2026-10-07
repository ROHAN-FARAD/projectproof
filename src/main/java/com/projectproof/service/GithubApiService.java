package com.projectproof.service;

import java.util.List;
import java.util.Map;

public interface GithubApiService {

    List<Map<String, Object>> getRepositoryCommits(
            String owner,
            String repositoryName
    );
}