package com.dongrame.api.domain.review.dao;

import com.dongrame.api.domain.review.entity.Review;
import com.dongrame.api.domain.review.entity.ReviewLike;
import com.dongrame.api.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {
    ReviewLike findByReviewAndUser(Review review, User user);
}
