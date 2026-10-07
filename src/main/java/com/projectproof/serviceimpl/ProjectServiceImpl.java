package com.projectproof.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.projectproof.dto.project.CreateProjectRequest;
import com.projectproof.dto.project.ProjectResponse;
import com.projectproof.dto.project.UpdateProjectRequest;
import com.projectproof.entity.Project;
import com.projectproof.entity.ProjectStatus;
import com.projectproof.entity.User;
import com.projectproof.exception.ResourceNotFoundException;
import com.projectproof.repository.ProjectRepository;
import com.projectproof.repository.UserRepository;
import com.projectproof.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectServiceImpl(
            ProjectRepository projectRepository,
            UserRepository userRepository) {

        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ProjectResponse createProject(
            CreateProjectRequest request,
            String userEmail) {

        // Find logged-in user
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with email: " + userEmail));

        // Create project
        Project project = new Project();

        project.setProjectCode(generateProjectCode());
        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setProblemStatement(request.getProblemStatement());
        project.setObjectives(request.getObjectives());
        project.setDomain(request.getDomain());
        project.setTechnologyStack(request.getTechnologyStack());
        project.setExpectedOutcome(request.getExpectedOutcome());

        project.setStartDate(request.getStartDate());
        project.setExpectedEndDate(request.getExpectedEndDate());

        // Default project status
        project.setStatus(ProjectStatus.DRAFT);

        // Logged-in user becomes project creator
        project.setCreatedBy(user);

        // Save project
        Project savedProject = projectRepository.save(project);

        return mapToResponse(savedProject);
    }

    @Override
    public ProjectResponse getProjectById(Long id) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with id: " + id));

        return mapToResponse(project);
    }

    @Override
    public List<ProjectResponse> getAllProjects() {

        return projectRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
public List<ProjectResponse> getProjectsByCurrentUser(String userEmail) {

    User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "User not found with email: " + userEmail));

    return projectRepository.findByCreatedBy(user)
            .stream()
            .map(this::mapToResponse)
            .toList();
}

   @Override
public ProjectResponse updateProject(
        Long id,
        UpdateProjectRequest request,
        String userEmail) {

    User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "User not found with email: " + userEmail));

    Project project = projectRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Project not found with id: " + id));

    // Only project creator can update the project
    if (!project.getCreatedBy().getId().equals(user.getId())) {
        throw new IllegalArgumentException(
                "You are not authorized to update this project");
    }

    project.setTitle(request.getTitle());
    project.setDescription(request.getDescription());
    project.setProblemStatement(request.getProblemStatement());
    project.setObjectives(request.getObjectives());
    project.setDomain(request.getDomain());
    project.setTechnologyStack(request.getTechnologyStack());
    project.setExpectedOutcome(request.getExpectedOutcome());

    project.setStartDate(request.getStartDate());
    project.setExpectedEndDate(request.getExpectedEndDate());
    project.setActualEndDate(request.getActualEndDate());

    Project updatedProject = projectRepository.save(project);

    return mapToResponse(updatedProject);
}

    @Override
public void deleteProject(Long id, String userEmail) {

    User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "User not found with email: " + userEmail));

    Project project = projectRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Project not found with id: " + id));

    // Only project creator can delete the project
    if (!project.getCreatedBy().getId().equals(user.getId())) {
        throw new IllegalArgumentException(
                "You are not authorized to delete this project");
    }

    projectRepository.delete(project);
}

    // Generate unique ProjectProof project code
    private String generateProjectCode() {

        String code;

        do {
            int randomNumber = (int) (Math.random() * 9000) + 1000;

            code = "PP-" + java.time.Year.now().getValue()
                    + "-" + randomNumber;

        } while (projectRepository.existsByProjectCode(code));

        return code;
    }

    // Convert Project Entity → ProjectResponse DTO
    private ProjectResponse mapToResponse(Project project) {

        ProjectResponse response = new ProjectResponse();

        response.setId(project.getId());
        response.setProjectCode(project.getProjectCode());
        response.setTitle(project.getTitle());
        response.setDescription(project.getDescription());
        response.setProblemStatement(project.getProblemStatement());
        response.setObjectives(project.getObjectives());
        response.setDomain(project.getDomain());
        response.setTechnologyStack(project.getTechnologyStack());
        response.setExpectedOutcome(project.getExpectedOutcome());

        response.setStartDate(project.getStartDate());
        response.setExpectedEndDate(project.getExpectedEndDate());
        response.setActualEndDate(project.getActualEndDate());

        response.setStatus(project.getStatus());

        if (project.getCreatedBy() != null) {
            response.setCreatedById(project.getCreatedBy().getId());
            response.setCreatedByName(project.getCreatedBy().getName());
        }

        if (project.getMentor() != null) {
            response.setMentorId(project.getMentor().getId());
            response.setMentorName(project.getMentor().getName());
        }

        response.setCreatedAt(project.getCreatedAt());
        response.setUpdatedAt(project.getUpdatedAt());

        return response;
    }
}