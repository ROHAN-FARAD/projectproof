package com.projectproof.service;

import com.projectproof.dto.evidence.EvidenceRequest;
import com.projectproof.dto.evidence.EvidenceResponse;

import java.util.List;

public interface EvidenceService {

    EvidenceResponse submitEvidence(
            Long milestoneId,
            EvidenceRequest request,
            String userEmail);

    List<EvidenceResponse> getMilestoneEvidence(Long milestoneId);

    EvidenceResponse getEvidenceById(Long evidenceId);

    EvidenceResponse updateEvidence(
            Long evidenceId,
            EvidenceRequest request,
            String userEmail);

    void deleteEvidence(
            Long evidenceId,
            String userEmail);
}