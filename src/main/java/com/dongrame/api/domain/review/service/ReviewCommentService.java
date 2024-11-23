package com.dongrame.api.domain.review.service;

import com.dongrame.api.domain.review.dao.CommentLikeRepository;
import com.dongrame.api.domain.review.dao.ReviewCommentRepository;
import com.dongrame.api.domain.review.dao.ReviewRepository;
import com.dongrame.api.domain.review.dto.GetReviewCommentResponseDTO;
import com.dongrame.api.domain.review.dto.PatchCommentRequestDTO;
import com.dongrame.api.domain.review.dto.PostCommentRequestDTO;
import com.dongrame.api.domain.review.dto.UserInfoDTO;
import com.dongrame.api.domain.review.entity.CommentLike;
import com.dongrame.api.domain.review.entity.Review;
import com.dongrame.api.domain.review.entity.ReviewComment;
import com.dongrame.api.domain.user.entity.User;
import com.dongrame.api.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewCommentService {
    private final ReviewCommentRepository reviewCommentRepository;
    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public ReviewComment saveReviewComment(PostCommentRequestDTO request) {
        User currentUser=userService.getCurrentUser();
        Review saveReview=reviewRepository.findById(request.getReviewId()).orElseThrow(()->new RuntimeException("찾을 수 없습니다"));
        saveReview.setCommentNum(saveReview.getCommentNum()+1);
        reviewRepository.save(saveReview);
        ReviewComment newReviewComment=ReviewComment.builder()
                .content(request.getComment())
                .user(currentUser)
                .review(saveReview)
                .build();
        return reviewCommentRepository.save(newReviewComment);
    }

    @Transactional
    public void updateReviewComment(PatchCommentRequestDTO request) {
        ReviewComment savedReviewComment=reviewCommentRepository.findById(request.getCommentId()).orElseThrow(()->new RuntimeException("찾을 수 없습니다"));
        if(!savedReviewComment.getUser().equals(userService.getCurrentUser())){
            throw new RuntimeException("권한이 없습니다");
        }
        savedReviewComment.setContent(request.getComment());
        reviewCommentRepository.save(savedReviewComment);
    }

    @Transactional
    public void deleteReviewComment(Long reviewCommentId) {
        ReviewComment savedReviewComment=reviewCommentRepository.findById(reviewCommentId).orElseThrow(()->new RuntimeException("찾을 수 없습니다"));
        if(!savedReviewComment.getUser().equals(userService.getCurrentUser())){
            throw new RuntimeException("권한이 없습니다");
        }
        Review saveReview=savedReviewComment.getReview();
        saveReview.setCommentNum(saveReview.getCommentNum()-1);
        reviewRepository.save(saveReview);
        reviewCommentRepository.delete(savedReviewComment);
    }

    @Transactional
    public List<GetReviewCommentResponseDTO> getReviewComment(Long reviewId) {
        List<ReviewComment> reviewComments=reviewCommentRepository.findByReviewIdAndUserActiveTrue(reviewId);

        List<GetReviewCommentResponseDTO> savedReviewComments= new ArrayList<>();
        for(ReviewComment reviewComment:reviewComments){
            CommentLike commentLike =commentLikeRepository.findByReviewCommentAndUser(reviewComment,userService.getCurrentUser());
            boolean liked=true;
            if(commentLike ==null){
                liked=false;
            }

            GetReviewCommentResponseDTO addComment=GetReviewCommentResponseDTO.builder()
                    .commentId(reviewComment.getId())
                    .userInfo(UserInfoDTO.toUserInfoDTO(reviewComment.getUser()))
                    .content(reviewComment.getContent())
                    .likeNum(reviewComment.getLikeNum())
                    .liked(liked)
                    .build();
            savedReviewComments.add(addComment);
        }
        return savedReviewComments;
    }
}
