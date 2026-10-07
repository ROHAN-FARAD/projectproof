package com.projectproof.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectproof.entity.Evidence;
import com.projectproof.entity.EvidenceStatus;
import com.projectproof.entity.Milestone;
import com.projectproof.entity.User;

public interface EvidenceRepository extends JpaRepository<Evidence, Long> {

    List<Evidence> findByMilestone(Milestone milestone);

    List<Evidence> findByUploadedBy(User user);

    List<Evidence> findByStatus(EvidenceStatus status);

    List<Evidence> findByMilestoneAndStatus(
            Milestone milestone,
            EvidenceStatus status
    );
}