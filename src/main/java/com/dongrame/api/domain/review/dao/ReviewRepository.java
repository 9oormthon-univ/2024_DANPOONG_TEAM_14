package com.dongrame.api.domain.review.dao;

import com.dongrame.api.domain.review.entity.Review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByPlaceIdAndUserActiveTrue(Long placeId);
    List<Review> findByUserIdAndUserActiveTrue(Long userId);

}
