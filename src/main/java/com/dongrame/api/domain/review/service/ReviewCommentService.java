package com.dongrame.api.domain.review.service;

import com.dongrame.api.domain.review.dao.ReviewCommentRepository;
import com.dongrame.api.domain.review.dao.ReviewRepository;
import com.dongrame.api.domain.review.dto.GetReviewCommentResponseDTO;
import com.dongrame.api.domain.review.dto.PostCommentRequestDTO;
import com.dongrame.api.domain.review.entity.Review;
import com.dongrame.api.domain.review.entity.ReviewComment;
import com.dongrame.api.domain.user.dao.UserRepository;
import com.dongrame.api.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewCommentService {
    private final ReviewCommentRepository reviewCommentRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @Transactional
    public ReviewComment postReviewComment(PostCommentRequestDTO request) {
        User saveUser =userRepository.findById(request.getUserId()).orElseThrow(()->new RuntimeException("찾을 수 없습니다"));
        Review saveReview=reviewRepository.findById(request.getReviewId()).orElseThrow(()->new RuntimeException("찾을 수 없습니다"));
        ReviewComment newReviewComment=ReviewComment.builder()
                .content(request.getComment())
                .user(saveUser)
                .review(saveReview)
                .build();
        return reviewCommentRepository.save(newReviewComment);
    }

    @Transactional
    public void deleteReviewComment(Long reviewCommentId) {
        reviewCommentRepository.deleteById(reviewCommentId);
    }

    @Transactional
    public List<GetReviewCommentResponseDTO> getReviewComment(Long reviewId) {
        List<ReviewComment> reviewComments=reviewCommentRepository.findByReviewId(reviewId);
        List<GetReviewCommentResponseDTO> savedReviewComments= new ArrayList<>();
        for(ReviewComment reviewComment:reviewComments){
            GetReviewCommentResponseDTO addComment=GetReviewCommentResponseDTO.builder()
                    .commentId(reviewComment.getId())
                    .userId(reviewComment.getUser().getId())
                    .content(reviewComment.getContent())
                    .build();
            savedReviewComments.add(addComment);
        }
        return savedReviewComments;
    }
}
