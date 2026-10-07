package com.projectproof.repository;

import com.projectproof.entity.Project;
import com.projectproof.entity.VerificationReport;
import com.projectproof.entity.VerificationStatus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VerificationReportRepository
        extends JpaRepository<VerificationReport, Long> {

    Optional<VerificationReport> findByProject(Project project);

    Optional<VerificationReport> findByVerificationId(String verificationId);

    boolean existsByProject(Project project);

    List<VerificationReport> findByStatus(VerificationStatus status);
}