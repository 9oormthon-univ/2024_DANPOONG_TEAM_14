package com.dongrame.api.domain.review.dao;

import com.dongrame.api.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
