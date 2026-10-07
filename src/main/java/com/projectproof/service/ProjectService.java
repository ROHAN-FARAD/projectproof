package com.projectproof.service;

import java.util.List;

import com.projectproof.dto.project.CreateProjectRequest;
import com.projectproof.dto.project.ProjectResponse;
import com.projectproof.dto.project.UpdateProjectRequest;

public interface ProjectService {

    ProjectResponse createProject(CreateProjectRequest request, String userEmail);

    ProjectResponse getProjectById(Long id);

    List<ProjectResponse> getAllProjects();

    List<ProjectResponse> getProjectsByCurrentUser(String userEmail);

    ProjectResponse updateProject(Long id, UpdateProjectRequest request, String userEmail);

    void deleteProject(Long id, String userEmail);
}