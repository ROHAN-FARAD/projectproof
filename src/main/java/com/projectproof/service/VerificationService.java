package com.projectproof.service;

import com.projectproof.dto.verification.VerificationResponse;

public interface VerificationService {

    VerificationResponse generateVerificationReport(Long projectId);

    VerificationResponse getVerificationReport(Long projectId);

    VerificationResponse getVerificationById(Long verificationId);

}