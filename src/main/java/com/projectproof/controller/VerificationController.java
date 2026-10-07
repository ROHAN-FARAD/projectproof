package com.projectproof.controller;

import com.projectproof.dto.verification.VerificationResponse;
import com.projectproof.service.VerificationService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class VerificationController {

    private final VerificationService verificationService;

    public VerificationController(VerificationService verificationService) {
        this.verificationService = verificationService;
    }

    @PostMapping("/projects/{projectId}/verification")
    public VerificationResponse generateVerificationReport(
            @PathVariable Long projectId) {

        return verificationService.generateVerificationReport(projectId);
    }

    @GetMapping("/projects/{projectId}/verification")
    public VerificationResponse getVerificationReport(
            @PathVariable Long projectId) {

        return verificationService.getVerificationReport(projectId);
    }

    @GetMapping("/verification/{verificationId}")
    public VerificationResponse getVerificationById(
            @PathVariable Long verificationId) {

        return verificationService.getVerificationById(verificationId);
    }
}