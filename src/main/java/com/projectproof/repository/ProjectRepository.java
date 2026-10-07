package com.projectproof.repository;

import com.projectproof.entity.Project;
import com.projectproof.entity.ProjectStatus;
import com.projectproof.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<Project> findByProjectCode(String projectCode);

    boolean existsByProjectCode(String projectCode);

    List<Project> findByCreatedBy(User user);

    List<Project> findByMentor(User mentor);

    List<Project> findByStatus(ProjectStatus status);
}