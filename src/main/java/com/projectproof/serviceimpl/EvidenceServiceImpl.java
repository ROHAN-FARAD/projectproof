package com.projectproof.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projectproof.dto.evidence.EvidenceRequest;
import com.projectproof.dto.evidence.EvidenceResponse;
import com.projectproof.entity.Evidence;
import com.projectproof.entity.EvidenceStatus;
import com.projectproof.entity.Milestone;
import com.projectproof.entity.Project;
import com.projectproof.entity.User;
import com.projectproof.exception.BadRequestException;
import com.projectproof.exception.ResourceNotFoundException;
import com.projectproof.repository.EvidenceRepository;
import com.projectproof.repository.MilestoneRepository;
import com.projectproof.repository.UserRepository;
import com.projectproof.service.EvidenceService;

@Service
@Transactional
public class EvidenceServiceImpl implements EvidenceService {

    private final EvidenceRepository evidenceRepository;
    private final MilestoneRepository milestoneRepository;
    private final UserRepository userRepository;

    public EvidenceServiceImpl(
            EvidenceRepository evidenceRepository,
            MilestoneRepository milestoneRepository,
            UserRepository userRepository) {

        this.evidenceRepository = evidenceRepository;
        this.milestoneRepository = milestoneRepository;
        this.userRepository = userRepository;
    }

    @Override
    public EvidenceResponse submitEvidence(
            Long milestoneId,
            EvidenceRequest request,
            String userEmail) {

        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with email: " + userEmail));

        Milestone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Milestone not found with id: " + milestoneId));

        Evidence evidence = new Evidence();

        evidence.setMilestone(milestone);
        evidence.setUploadedBy(currentUser);
        evidence.setType(request.getType());
        evidence.setTitle(request.getTitle());
        evidence.setDescription(request.getDescription());
        evidence.setFileUrl(request.getFileUrl());
        evidence.setExternalUrl(request.getExternalUrl());
        evidence.setSubmittedAt(LocalDateTime.now());
        evidence.setStatus(EvidenceStatus.SUBMITTED);

        Evidence savedEvidence =
                evidenceRepository.save(evidence);

        return mapToResponse(savedEvidence);
    }

    @Override
    public List<EvidenceResponse> getMilestoneEvidence(
            Long milestoneId) {

        Milestone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Milestone not found with id: " + milestoneId));

        return evidenceRepository.findByMilestone(milestone)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public EvidenceResponse getEvidenceById(
            Long evidenceId) {

        Evidence evidence = evidenceRepository.findById(evidenceId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Evidence not found with id: " + evidenceId));

        return mapToResponse(evidence);
    }

    @Override
    public EvidenceResponse updateEvidence(
            Long evidenceId,
            EvidenceRequest request,
            String userEmail) {

        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with email: " + userEmail));

        Evidence evidence = evidenceRepository.findById(evidenceId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Evidence not found with id: " + evidenceId));

        if (!evidence.getUploadedBy().getId()
                .equals(currentUser.getId())) {

            throw new BadRequestException(
                    "Only the evidence owner can update this evidence");
        }

        evidence.setType(request.getType());
        evidence.setTitle(request.getTitle());
        evidence.setDescription(request.getDescription());
        evidence.setFileUrl(request.getFileUrl());
        evidence.setExternalUrl(request.getExternalUrl());

        Evidence updatedEvidence =
                evidenceRepository.save(evidence);

        return mapToResponse(updatedEvidence);
    }

    @Override
    public void deleteEvidence(
            Long evidenceId,
            String userEmail) {

        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with email: " + userEmail));

        Evidence evidence = evidenceRepository.findById(evidenceId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Evidence not found with id: " + evidenceId));

        if (!evidence.getUploadedBy().getId()
                .equals(currentUser.getId())) {

            throw new BadRequestException(
                    "Only the evidence owner can delete this evidence");
        }

        evidenceRepository.delete(evidence);
    }

    private EvidenceResponse mapToResponse(
            Evidence evidence) {

        EvidenceResponse response =
                new EvidenceResponse();

        response.setId(evidence.getId());

        if (evidence.getMilestone() != null) {

            response.setMilestoneId(
                    evidence.getMilestone().getId());

            response.setMilestoneTitle(
                    evidence.getMilestone().getTitle());
        }

        if (evidence.getUploadedBy() != null) {

            response.setUploadedById(
                    evidence.getUploadedBy().getId());

            response.setUploadedByName(
                    evidence.getUploadedBy().getName());

            response.setUploadedByEmail(
                    evidence.getUploadedBy().getEmail());
        }

        response.setType(evidence.getType());
        response.setTitle(evidence.getTitle());
        response.setDescription(evidence.getDescription());
        response.setFileUrl(evidence.getFileUrl());
        response.setExternalUrl(evidence.getExternalUrl());
        response.setSubmittedAt(evidence.getSubmittedAt());
        response.setStatus(evidence.getStatus());

        return response;
    }
}