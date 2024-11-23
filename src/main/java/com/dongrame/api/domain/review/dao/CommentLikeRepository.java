package com.dongrame.api.domain.review.dao;

import com.dongrame.api.domain.review.entity.CommentLike;
import com.dongrame.api.domain.review.entity.ReviewComment;
import com.dongrame.api.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    CommentLike findByReviewCommentAndUser(ReviewComment reviewComment, User user);

}
