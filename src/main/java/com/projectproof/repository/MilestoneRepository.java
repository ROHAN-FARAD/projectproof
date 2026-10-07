package com.projectproof.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectproof.entity.Milestone;
import com.projectproof.entity.MilestoneStatus;
import com.projectproof.entity.Project;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {

    List<Milestone> findByProject(Project project);

    List<Milestone> findByProjectAndStatus(
            Project project,
            MilestoneStatus status
    );

    List<Milestone> findByStatus(MilestoneStatus status);
}