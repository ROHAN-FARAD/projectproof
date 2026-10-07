package com.projectproof.controller;

import com.projectproof.dto.timeline.TimelineResponse;
import com.projectproof.entity.TimelineEventType;
import com.projectproof.service.TimelineService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TimelineController {

    private final TimelineService timelineService;

    public TimelineController(TimelineService timelineService) {
        this.timelineService = timelineService;
    }

    // Create timeline event
    @PostMapping("/projects/{projectId}/timeline")
    public TimelineResponse createTimelineEvent(
            @PathVariable Long projectId,
            @RequestParam Long userId,
            @RequestParam TimelineEventType eventType,
            @RequestParam String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Long referenceId) {

        return timelineService.createTimelineEvent(
                projectId,
                userId,
                eventType,
                title,
                description,
                referenceId);
    }

    // Get complete project timeline
    @GetMapping("/projects/{projectId}/timeline")
    public List<TimelineResponse> getProjectTimeline(
            @PathVariable Long projectId) {

        return timelineService.getProjectTimeline(projectId);
    }

    // Get timeline event by ID
    @GetMapping("/timeline/{timelineId}")
    public TimelineResponse getTimelineEventById(
            @PathVariable Long timelineId) {

        return timelineService.getTimelineEventById(timelineId);
    }
}