package com.projectproof.service;

import com.projectproof.dto.mentorreview.MentorReviewRequest;
import com.projectproof.dto.mentorreview.MentorReviewResponse;

import java.util.List;

public interface MentorReviewService {

    // Create a mentor review for a milestone
    MentorReviewResponse createReview(
            Long milestoneId,
            MentorReviewRequest request,
            String mentorEmail);

    // Get all reviews for a milestone
    List<MentorReviewResponse> getMilestoneReviews(Long milestoneId);

    // Get review by ID
    MentorReviewResponse getReviewById(Long reviewId);

    // Update a review
    MentorReviewResponse updateReview(
            Long reviewId,
            MentorReviewRequest request,
            String mentorEmail);

    // Delete a review
    void deleteReview(Long reviewId, String mentorEmail);
}