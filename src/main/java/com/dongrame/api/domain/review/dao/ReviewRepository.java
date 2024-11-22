package com.dongrame.api.domain.review.dao;

import com.dongrame.api.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findByPlaceId(Long placeId,  PageRequest pageRequest);
    Page<Review> findByUserId(Long userId, PageRequest pageRequest);

}
