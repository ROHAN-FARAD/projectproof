package com.projectproof.serviceimpl;

import com.projectproof.dto.mentorreview.MentorReviewRequest;
import com.projectproof.dto.mentorreview.MentorReviewResponse;
import com.projectproof.entity.MentorReview;
import com.projectproof.entity.MentorReviewStatus;
import com.projectproof.entity.Milestone;
import com.projectproof.entity.User;
import com.projectproof.exception.BadRequestException;
import com.projectproof.exception.ResourceNotFoundException;
import com.projectproof.repository.MentorReviewRepository;
import com.projectproof.repository.MilestoneRepository;
import com.projectproof.repository.UserRepository;
import com.projectproof.service.MentorReviewService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class MentorReviewServiceImpl implements MentorReviewService {

    private final MentorReviewRepository mentorReviewRepository;
    private final MilestoneRepository milestoneRepository;
    private final UserRepository userRepository;

    public MentorReviewServiceImpl(
            MentorReviewRepository mentorReviewRepository,
            MilestoneRepository milestoneRepository,
            UserRepository userRepository) {

        this.mentorReviewRepository = mentorReviewRepository;
        this.milestoneRepository = milestoneRepository;
        this.userRepository = userRepository;
    }

    @Override
    public MentorReviewResponse createReview(
            Long milestoneId,
            MentorReviewRequest request,
            String mentorEmail) {

        User mentor = userRepository.findByEmail(mentorEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with email: " + mentorEmail));

        Milestone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Milestone not found with id: " + milestoneId));

        // Only MENTOR can create mentor reviews
        if (mentor.getRole() == null ||
                !"MENTOR".equalsIgnoreCase(mentor.getRole().getName())) {

            throw new BadRequestException(
                    "Only mentors can create milestone reviews");
        }

        MentorReview review = new MentorReview();

        review.setMilestone(milestone);
        review.setMentor(mentor);

        try {
            review.setStatus(
                    MentorReviewStatus.valueOf(
                            request.getStatus().toUpperCase()));
        } catch (IllegalArgumentException e) {

            throw new BadRequestException(
                    "Invalid review status: " + request.getStatus());
        }

        review.setComments(request.getComments());
        review.setReviewedAt(LocalDateTime.now());

        return mapToResponse(
                mentorReviewRepository.save(review));
    }

    @Override
    public List<MentorReviewResponse> getMilestoneReviews(
            Long milestoneId) {

        Milestone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Milestone not found with id: " + milestoneId));

        return mentorReviewRepository
                .findByMilestone(milestone)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public MentorReviewResponse getReviewById(Long reviewId) {

        MentorReview review = mentorReviewRepository.findById(reviewId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Mentor review not found with id: " + reviewId));

        return mapToResponse(review);
    }

    @Override
    public MentorReviewResponse updateReview(
            Long reviewId,
            MentorReviewRequest request,
            String mentorEmail) {

        User mentor = userRepository.findByEmail(mentorEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with email: " + mentorEmail));

        MentorReview review = mentorReviewRepository.findById(reviewId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Mentor review not found with id: " + reviewId));

        // Only the mentor who created the review can update it
        if (!review.getMentor().getId().equals(mentor.getId())) {

            throw new BadRequestException(
                    "Only the review mentor can update this review");
        }

        try {
            review.setStatus(
                    MentorReviewStatus.valueOf(
                            request.getStatus().toUpperCase()));
        } catch (IllegalArgumentException e) {

            throw new BadRequestException(
                    "Invalid review status: " + request.getStatus());
        }

        review.setComments(request.getComments());
        review.setReviewedAt(LocalDateTime.now());

        return mapToResponse(
                mentorReviewRepository.save(review));
    }

    @Override
    public void deleteReview(
            Long reviewId,
            String mentorEmail) {

        User mentor = userRepository.findByEmail(mentorEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with email: " + mentorEmail));

        MentorReview review = mentorReviewRepository.findById(reviewId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Mentor review not found with id: " + reviewId));

        // Only the mentor who created the review can delete it
        if (!review.getMentor().getId().equals(mentor.getId())) {

            throw new BadRequestException(
                    "Only the review mentor can delete this review");
        }

        mentorReviewRepository.delete(review);
    }

    private MentorReviewResponse mapToResponse(
            MentorReview review) {

        MentorReviewResponse response =
                new MentorReviewResponse();

        response.setId(review.getId());

        if (review.getMilestone() != null) {

            response.setMilestoneId(
                    review.getMilestone().getId());

            response.setMilestoneTitle(
                    review.getMilestone().getTitle());
        }

        if (review.getMentor() != null) {

            response.setMentorId(
                    review.getMentor().getId());

            response.setMentorName(
                    review.getMentor().getName());

            response.setMentorEmail(
                    review.getMentor().getEmail());
        }

        response.setStatus(review.getStatus());
        response.setComments(review.getComments());
        response.setReviewedAt(review.getReviewedAt());

        return response;
    }
}