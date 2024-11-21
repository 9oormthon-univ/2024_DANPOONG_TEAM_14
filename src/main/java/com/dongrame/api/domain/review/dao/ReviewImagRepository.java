package com.dongrame.api.domain.review.dao;

import com.dongrame.api.domain.review.entity.ReviewImag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewImagRepository extends JpaRepository<ReviewImag, Long> {
    List<ReviewImag> findByReviewId(Long reviewId);
}