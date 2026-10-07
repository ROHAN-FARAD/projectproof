package com.projectproof.serviceimpl;

import com.projectproof.dto.timeline.TimelineResponse;
import com.projectproof.entity.Project;
import com.projectproof.entity.ProjectTimeline;
import com.projectproof.entity.TimelineEventType;
import com.projectproof.entity.User;
import com.projectproof.exception.ResourceNotFoundException;
import com.projectproof.repository.ProjectRepository;
import com.projectproof.repository.ProjectTimelineRepository;
import com.projectproof.repository.UserRepository;
import com.projectproof.service.TimelineService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TimelineServiceImpl implements TimelineService {

    private final ProjectTimelineRepository projectTimelineRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public TimelineServiceImpl(
            ProjectTimelineRepository projectTimelineRepository,
            ProjectRepository projectRepository,
            UserRepository userRepository) {

        this.projectTimelineRepository = projectTimelineRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TimelineResponse createTimelineEvent(
            Long projectId,
            Long userId,
            TimelineEventType eventType,
            String title,
            String description,
            Long referenceId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with id: " + projectId));

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + userId));

        ProjectTimeline timeline = new ProjectTimeline();

        timeline.setProject(project);
        timeline.setUser(user);
        timeline.setEventType(eventType);
        timeline.setTitle(title);
        timeline.setDescription(description);
        timeline.setReferenceId(referenceId);
        timeline.setCreatedAt(LocalDateTime.now());

        ProjectTimeline savedTimeline =
                projectTimelineRepository.save(timeline);

        return mapToResponse(savedTimeline);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TimelineResponse> getProjectTimeline(Long projectId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with id: " + projectId));

        return projectTimelineRepository
                .findByProjectOrderByCreatedAtAsc(project)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TimelineResponse getTimelineEventById(Long timelineId) {

        ProjectTimeline timeline =
                projectTimelineRepository.findById(timelineId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Timeline event not found with id: "
                                                + timelineId));

        return mapToResponse(timeline);
    }

    private TimelineResponse mapToResponse(
            ProjectTimeline timeline) {

        TimelineResponse response = new TimelineResponse();

        response.setId(timeline.getId());

        if (timeline.getProject() != null) {
            response.setProjectId(
                    timeline.getProject().getId());
        }

        if (timeline.getUser() != null) {
            response.setUserId(
                    timeline.getUser().getId());

            response.setUserName(
                    timeline.getUser().getName());
        }

        response.setEventType(
                timeline.getEventType());

        response.setTitle(
                timeline.getTitle());

        response.setDescription(
                timeline.getDescription());

        response.setReferenceId(
                timeline.getReferenceId());

        response.setCreatedAt(
                timeline.getCreatedAt());

        return response;
    }
}