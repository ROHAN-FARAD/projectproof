package com.projectproof.controller;

import com.projectproof.dto.mentorreview.MentorReviewRequest;
import com.projectproof.dto.mentorreview.MentorReviewResponse;
import com.projectproof.service.MentorReviewService;

import jakarta.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class MentorReviewController {

    private final MentorReviewService mentorReviewService;

    public MentorReviewController(
            MentorReviewService mentorReviewService) {

        this.mentorReviewService = mentorReviewService;
    }

    // Create mentor review for a milestone
    @PostMapping("/milestones/{milestoneId}/reviews")
    public MentorReviewResponse createReview(
            @PathVariable Long milestoneId,
            @Valid @RequestBody MentorReviewRequest request,
            Authentication authentication) {

        return mentorReviewService.createReview(
                milestoneId,
                request,
                authentication.getName());
    }

    // Get all reviews for a milestone
    @GetMapping("/milestones/{milestoneId}/reviews")
    public List<MentorReviewResponse> getMilestoneReviews(
            @PathVariable Long milestoneId) {

        return mentorReviewService.getMilestoneReviews(
                milestoneId);
    }

    // Get review by ID
    @GetMapping("/reviews/{reviewId}")
    public MentorReviewResponse getReviewById(
            @PathVariable Long reviewId) {

        return mentorReviewService.getReviewById(reviewId);
    }

    // Update mentor review
    @PutMapping("/reviews/{reviewId}")
    public MentorReviewResponse updateReview(
            @PathVariable Long reviewId,
            @Valid @RequestBody MentorReviewRequest request,
            Authentication authentication) {

        return mentorReviewService.updateReview(
                reviewId,
                request,
                authentication.getName());
    }

    // Delete mentor review
    @DeleteMapping("/reviews/{reviewId}")
    public String deleteReview(
            @PathVariable Long reviewId,
            Authentication authentication) {

        mentorReviewService.deleteReview(
                reviewId,
                authentication.getName());

        return "Mentor review deleted successfully";
    }
}