package com.projectproof.serviceimpl;

import com.projectproof.entity.GithubRepository;
import com.projectproof.entity.Project;
import com.projectproof.exception.ResourceNotFoundException;
import com.projectproof.repository.GithubRepositoryRepository;
import com.projectproof.repository.ProjectRepository;
import com.projectproof.service.GithubRepositoryService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class GithubRepositoryServiceImpl
        implements GithubRepositoryService {

    private final GithubRepositoryRepository githubRepositoryRepository;
    private final ProjectRepository projectRepository;

    public GithubRepositoryServiceImpl(
            GithubRepositoryRepository githubRepositoryRepository,
            ProjectRepository projectRepository) {

        this.githubRepositoryRepository = githubRepositoryRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public GithubRepository connectRepository(
            Long projectId,
            String repositoryUrl,
            String repositoryName,
            String ownerName,
            Boolean isPrivate) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with id: " + projectId));

        if (githubRepositoryRepository.findByProject(project).isPresent()) {
            throw new IllegalStateException(
                    "A GitHub repository is already connected to this project");
        }

        GithubRepository repository = new GithubRepository();

        repository.setProject(project);
        repository.setRepositoryUrl(repositoryUrl);
        repository.setRepositoryName(repositoryName);
        repository.setOwnerName(ownerName);
        repository.setConnectedAt(LocalDateTime.now());
        repository.setIsPrivate(
                isPrivate != null && isPrivate
        );

        return githubRepositoryRepository.save(repository);
    }

    @Override
    @Transactional(readOnly = true)
    public GithubRepository getRepositoryByProject(Long projectId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with id: " + projectId));

        return githubRepositoryRepository.findByProject(project)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "GitHub repository not found for project id: "
                                        + projectId));
    }

    @Override
    @Transactional(readOnly = true)
    public GithubRepository getRepositoryById(Long repositoryId) {

        return githubRepositoryRepository.findById(repositoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "GitHub repository not found with id: "
                                        + repositoryId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GithubRepository> getAllRepositories() {

        return githubRepositoryRepository.findAll();
    }

    @Override
    public GithubRepository updateRepository(
            Long repositoryId,
            String repositoryUrl,
            String repositoryName,
            String ownerName,
            Boolean isPrivate) {

        GithubRepository repository =
                githubRepositoryRepository.findById(repositoryId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "GitHub repository not found with id: "
                                                + repositoryId));

        repository.setRepositoryUrl(repositoryUrl);
        repository.setRepositoryName(repositoryName);
        repository.setOwnerName(ownerName);

        if (isPrivate != null) {
            repository.setIsPrivate(isPrivate);
        }

        return githubRepositoryRepository.save(repository);
    }

    @Override
    public void deleteRepository(Long repositoryId) {

        GithubRepository repository =
                githubRepositoryRepository.findById(repositoryId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "GitHub repository not found with id: "
                                                + repositoryId));

        githubRepositoryRepository.delete(repository);
    }
}