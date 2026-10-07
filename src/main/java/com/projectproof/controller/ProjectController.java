package com.projectproof.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectproof.dto.project.CreateProjectRequest;
import com.projectproof.dto.project.ProjectResponse;
import com.projectproof.dto.project.UpdateProjectRequest;
import com.projectproof.service.ProjectService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ProjectResponse createProject(
            @Valid @RequestBody CreateProjectRequest request,
            Authentication authentication) {

        return projectService.createProject(
                request,
                authentication.getName());
    }

    @GetMapping
    public List<ProjectResponse> getAllProjects() {

        return projectService.getAllProjects();
    }

    @GetMapping("/my")
    public List<ProjectResponse> getMyProjects(
            Authentication authentication) {

        return projectService.getProjectsByCurrentUser(
                authentication.getName());
    }

    @GetMapping("/{id}")
    public ProjectResponse getProjectById(
            @PathVariable Long id) {

        return projectService.getProjectById(id);
    }

    @PutMapping("/{id}")
    public ProjectResponse updateProject(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProjectRequest request,
            Authentication authentication) {

        return projectService.updateProject(
                id,
                request,
                authentication.getName());
    }

    @DeleteMapping("/{id}")
    public String deleteProject(
            @PathVariable Long id,
            Authentication authentication) {

        projectService.deleteProject(
                id,
                authentication.getName());

        return "Project deleted successfully";
    }
}