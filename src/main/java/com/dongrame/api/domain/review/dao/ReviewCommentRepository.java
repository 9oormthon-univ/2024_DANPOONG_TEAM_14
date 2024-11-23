package com.dongrame.api.domain.review.dao;

import com.dongrame.api.domain.review.entity.ReviewComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewCommentRepository extends JpaRepository<ReviewComment, Long> {
    List<ReviewComment> findByReviewIdAndUserActiveTrue(Long reviewId);
    List<ReviewComment> findByUserIdAndUserActiveTrue(Long userId);

}
