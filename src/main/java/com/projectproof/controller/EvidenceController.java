package com.projectproof.controller;

import com.projectproof.dto.evidence.EvidenceRequest;
import com.projectproof.dto.evidence.EvidenceResponse;
import com.projectproof.service.EvidenceService;

import jakarta.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EvidenceController {

    private final EvidenceService evidenceService;

    public EvidenceController(EvidenceService evidenceService) {
        this.evidenceService = evidenceService;
    }

    // Submit evidence for a milestone
    @PostMapping("/milestones/{milestoneId}/evidence")
    public EvidenceResponse submitEvidence(
            @PathVariable Long milestoneId,
            @Valid @RequestBody EvidenceRequest request,
            Authentication authentication) {

        return evidenceService.submitEvidence(
                milestoneId,
                request,
                authentication.getName());
    }

    // Get all evidence for a milestone
    @GetMapping("/milestones/{milestoneId}/evidence")
    public List<EvidenceResponse> getMilestoneEvidence(
            @PathVariable Long milestoneId) {

        return evidenceService.getMilestoneEvidence(milestoneId);
    }

    // Get evidence by ID
    @GetMapping("/evidence/{evidenceId}")
    public EvidenceResponse getEvidenceById(
            @PathVariable Long evidenceId) {

        return evidenceService.getEvidenceById(evidenceId);
    }

    // Update evidence
    @PutMapping("/evidence/{evidenceId}")
    public EvidenceResponse updateEvidence(
            @PathVariable Long evidenceId,
            @Valid @RequestBody EvidenceRequest request,
            Authentication authentication) {

        return evidenceService.updateEvidence(
                evidenceId,
                request,
                authentication.getName());
    }

    // Delete evidence
    @DeleteMapping("/evidence/{evidenceId}")
    public String deleteEvidence(
            @PathVariable Long evidenceId,
            Authentication authentication) {

        evidenceService.deleteEvidence(
                evidenceId,
                authentication.getName());

        return "Evidence deleted successfully";
    }
}