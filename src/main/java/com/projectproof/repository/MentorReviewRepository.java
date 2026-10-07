package com.projectproof.repository;

import com.projectproof.entity.MentorReview;
import com.projectproof.entity.MentorReviewStatus;
import com.projectproof.entity.Milestone;
import com.projectproof.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MentorReviewRepository
        extends JpaRepository<MentorReview, Long> {

    // Get all reviews for a milestone
    List<MentorReview> findByMilestone(Milestone milestone);

    // Get all reviews created by a mentor
    List<MentorReview> findByMentor(User mentor);

    // Get reviews by status
    List<MentorReview> findByStatus(MentorReviewStatus status);

    // Get reviews for a milestone with a specific status
    List<MentorReview> findByMilestoneAndStatus(
            Milestone milestone,
            MentorReviewStatus status);
}