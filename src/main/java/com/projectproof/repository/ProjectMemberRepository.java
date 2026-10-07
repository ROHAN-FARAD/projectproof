package com.projectproof.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectproof.entity.Project;
import com.projectproof.entity.ProjectMember;
import com.projectproof.entity.User;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {

    List<ProjectMember> findByProject(Project project);

    List<ProjectMember> findByUser(User user);

    Optional<ProjectMember> findByProjectAndUser(Project project, User user);

    boolean existsByProjectAndUser(Project project, User user);
}