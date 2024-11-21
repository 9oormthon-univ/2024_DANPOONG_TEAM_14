package com.dongrame.api.domain.review.service;

import com.dongrame.api.domain.review.dao.CommentLikeRepository;
import com.dongrame.api.domain.review.dao.ReviewCommentRepository;
import com.dongrame.api.domain.review.dto.CommentLikeDTO;
import com.dongrame.api.domain.review.entity.CommentLike;
import com.dongrame.api.domain.review.entity.ReviewComment;
import com.dongrame.api.domain.user.dao.UserRepository;
import com.dongrame.api.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentLikeService {
    private final CommentLikeRepository commentLikeRepository;
    private final ReviewCommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentLikeDTO postCommentLike(CommentLikeDTO requset) {
        ReviewComment savedReviewComment=commentRepository.findById(requset.getCommentId()).orElseThrow(() -> new RuntimeException("찾을 수 없습니다"));
        User savedUser=userRepository.findById(requset.getUserId()) .orElseThrow(() -> new RuntimeException("찾을 수 없습니다"));

        CommentLike existingCommentLike = commentLikeRepository.findByReviewCommentAndUser(savedReviewComment,savedUser);
        if (existingCommentLike != null) {
            throw new RuntimeException("이미 좋아요를 누르셨습니다."); // 또는 적절한 예외 처리
        }

        CommentLike savedCommentLike=CommentLike.builder()
                .reviewComment(savedReviewComment)
                .user(savedUser)
                .build();
        commentLikeRepository.save(savedCommentLike);
        savedReviewComment.setLikeNum(savedReviewComment.getLikeNum()+1);
        commentRepository.save(savedReviewComment);
        return CommentLikeDTO.builder()
                .commentId(requset.getCommentId())
                .userId(requset.getUserId())
                .build();
    }

    @Transactional
    public void deleteCommentLike(CommentLikeDTO requset) {
        ReviewComment savedReviewComment=commentRepository.findById(requset.getCommentId()).orElseThrow(() -> new RuntimeException("찾을 수 없습니다"));
        User savedUser=userRepository.findById(requset.getUserId()) .orElseThrow(() -> new RuntimeException("찾을 수 없습니다"));

        CommentLike existingCommentLike = commentLikeRepository.findByReviewCommentAndUser(savedReviewComment,savedUser);
        if (existingCommentLike == null) {
            throw new RuntimeException("찾을 수 없습니다");
        }
        commentLikeRepository.delete(existingCommentLike);
    }
}
