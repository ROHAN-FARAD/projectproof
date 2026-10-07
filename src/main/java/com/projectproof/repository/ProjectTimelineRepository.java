package com.projectproof.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectproof.entity.Project;
import com.projectproof.entity.ProjectTimeline;
import com.projectproof.entity.TimelineEventType;
import com.projectproof.entity.User;

public interface ProjectTimelineRepository extends JpaRepository<ProjectTimeline, Long> {

    List<ProjectTimeline> findByProjectOrderByCreatedAtAsc(Project project);

    List<ProjectTimeline> findByUser(User user);

    List<ProjectTimeline> findByEventType(TimelineEventType eventType);

    List<ProjectTimeline> findByProjectAndEventType(
            Project project,
            TimelineEventType eventType
    );
}