package com.projectproof.service;

import com.projectproof.dto.timeline.TimelineResponse;
import com.projectproof.entity.TimelineEventType;

import java.util.List;

public interface TimelineService {

    TimelineResponse createTimelineEvent(
            Long projectId,
            Long userId,
            TimelineEventType eventType,
            String title,
            String description,
            Long referenceId);

    List<TimelineResponse> getProjectTimeline(Long projectId);

    TimelineResponse getTimelineEventById(Long timelineId);
}