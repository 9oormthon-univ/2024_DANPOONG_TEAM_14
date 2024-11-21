package com.dongrame.api.domain.review.service;

import com.dongrame.api.domain.review.dao.ReviewLikeRepository;
import com.dongrame.api.domain.review.dao.ReviewRepository;
import com.dongrame.api.domain.review.dto.LikeDTO;
import com.dongrame.api.domain.review.entity.Review;
import com.dongrame.api.domain.review.entity.ReviewLike;
import com.dongrame.api.domain.user.dao.UserRepository;
import com.dongrame.api.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewLikeService {
    private final ReviewLikeRepository reviewLikeRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @Transactional
    public LikeDTO postLike(LikeDTO requset) {
        Review savedReview=reviewRepository.findById(requset.getReviewId()).orElseThrow(() -> new RuntimeException("찾을 수 없습니다"));
        User savedUser=userRepository.findById(requset.getUserId()) .orElseThrow(() -> new RuntimeException("찾을 수 없습니다"));

        ReviewLike existingReviewLike = reviewLikeRepository.findByReviewAndUser(savedReview,savedUser);
        if (existingReviewLike != null) {
            throw new RuntimeException("이미 좋아요를 누르셨습니다."); // 또는 적절한 예외 처리
        }

        ReviewLike savedReviewLike=ReviewLike.builder()
                .review(savedReview)
                .user(savedUser)
                .build();
        reviewLikeRepository.save(savedReviewLike);
        savedReview.setLikeNum(savedReview.getLikeNum()+1);
        reviewRepository.save(savedReview);
        return LikeDTO.builder()
                .reviewId(requset.getReviewId())
                .userId(requset.getUserId())
                .build();
    }

    @Transactional
    public void deleteLike(LikeDTO requset) {
        Review savedReview=reviewRepository.findById(requset.getReviewId()).orElseThrow(() -> new RuntimeException("찾을 수 없습니다"));
        User savedUser=userRepository.findById(requset.getUserId()) .orElseThrow(() -> new RuntimeException("찾을 수 없습니다"));

        ReviewLike existingReviewLike = reviewLikeRepository.findByReviewAndUser(savedReview,savedUser);
        if (existingReviewLike == null) {
            throw new RuntimeException("찾을 수 없습니다");
        }
        reviewLikeRepository.delete(existingReviewLike);
    }
}
