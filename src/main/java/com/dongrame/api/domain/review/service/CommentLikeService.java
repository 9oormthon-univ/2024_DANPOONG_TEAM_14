package com.dongrame.api.domain.review.service;

import com.dongrame.api.domain.review.dao.CommentLikeRepository;
import com.dongrame.api.domain.review.dao.ReviewCommentRepository;
import com.dongrame.api.domain.review.dto.CommentLikeDTO;
import com.dongrame.api.domain.review.entity.CommentLike;
import com.dongrame.api.domain.review.entity.ReviewComment;
import com.dongrame.api.domain.user.entity.User;
import com.dongrame.api.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentLikeService {
    private final CommentLikeRepository commentLikeRepository;
    private final ReviewCommentRepository commentRepository;
    private final UserService userService;

    @Transactional
    public CommentLikeDTO saveCommentLike(Long request) {
        ReviewComment savedReviewComment=commentRepository.findById(request).orElseThrow(() -> new RuntimeException("찾을 수 없습니다"));
        User currentUser=userService.getCurrentUser();

        CommentLike existingCommentLike = commentLikeRepository.findByReviewCommentAndUser(savedReviewComment,currentUser);
        if (existingCommentLike != null) {
            throw new RuntimeException("이미 좋아요를 누르셨습니다."); // 또는 적절한 예외 처리
        }

        CommentLike savedCommentLike=CommentLike.builder()
                .reviewComment(savedReviewComment)
                .user(currentUser)
                .build();
        savedReviewComment.setLikeNum(savedReviewComment.getLikeNum()+1);
        commentRepository.save(savedReviewComment);
        commentLikeRepository.save(savedCommentLike);
        return CommentLikeDTO.builder()
                .commentId(request)
                .userId(currentUser.getId())
                .build();
    }

    @Transactional
    public void deleteCommentLike(Long request) {
        ReviewComment savedReviewComment=commentRepository.findById(request).orElseThrow(() -> new RuntimeException("찾을 수 없습니다"));
        User currentUser=userService.getCurrentUser();

        CommentLike existingCommentLike = commentLikeRepository.findByReviewCommentAndUser(savedReviewComment,currentUser);
        if (existingCommentLike == null) {
            throw new RuntimeException("찾을 수 없습니다");
        }
        savedReviewComment.setLikeNum(savedReviewComment.getLikeNum()-1);
        commentRepository.save(savedReviewComment);
        commentLikeRepository.delete(existingCommentLike);
    }
}
