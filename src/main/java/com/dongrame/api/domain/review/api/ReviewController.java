package com.dongrame.api.domain.review.api;


import com.dongrame.api.domain.review.dto.*;
import com.dongrame.api.domain.review.entity.Review;
import com.dongrame.api.domain.review.service.CommentLikeService;
import com.dongrame.api.domain.review.service.ReviewCommentService;
import com.dongrame.api.domain.review.service.ReviewLikeService;
import com.dongrame.api.domain.review.service.ReviewService;
import com.dongrame.api.global.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewCommentService reviewCommentService;
    private final ReviewLikeService reviewLikeService;
    private final CommentLikeService commentLikeService;

    @PostMapping(value = "/postReview", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<PostReviewResponseDTO> postReview(@ModelAttribute PostReviewRequestDTO request) {
        Review newReview=reviewService.postReview(request);

        return ApiResponse.success(PostReviewResponseDTO.toReviewResponseDTO(newReview));
    }

    @GetMapping("/getReview")
    public ApiResponse<GetReviewResponseDTO> getReview(@RequestParam(name = "reviewId") Long request) {
        return ApiResponse.success(reviewService.getReview(request));
    }

    @PatchMapping(value ="/patchReview", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<PatchReviewResponseDTO> patchReview(@ModelAttribute PatchReviewRequestDTO request) {
        return ApiResponse.success(PatchReviewResponseDTO.toPatchReviewResponseDTO(reviewService.patchReview(request)));
    }

    @DeleteMapping("/deleteReview")
    public ApiResponse<String> deleteReview(@RequestParam(name = "reviewId") Long request) {
        reviewService.deleteReview(request);
        return ApiResponse.success("Review deleted.");
    }

    @PostMapping("/postComment")
    public ApiResponse<Long> postComment(@RequestBody PostCommentRequestDTO request) {
        return ApiResponse.success(reviewCommentService.postReviewComment(request).getId());
    }

    @DeleteMapping("/deleteComment")
    public ApiResponse<String> deleteComment(@RequestParam(name = "commentId") Long request) {
        reviewCommentService.deleteReviewComment(request);
        return ApiResponse.success("Comment deleted.");
    }

    @GetMapping("/getComment")
    public ApiResponse<List<GetReviewCommentResponseDTO>> getComment(@RequestParam(name = "reviewId") Long request) {
        return ApiResponse.success(reviewCommentService.getReviewComment(request));
    }

    @PostMapping("/postLike")
    public ApiResponse<LikeDTO> PostLike(@RequestBody LikeDTO request) {
        return ApiResponse.success(reviewLikeService.postLike(request));
    }

    @DeleteMapping("/deleteLike")
    public ApiResponse<String> DeleteLike(@RequestBody LikeDTO request) {
        reviewLikeService.deleteLike(request);
        return ApiResponse.success("Like deleted.");
    }

    @PostMapping("/postCommentLike")
    public ApiResponse<CommentLikeDTO> PostCommentLike(@RequestBody CommentLikeDTO request) {
        return ApiResponse.success(commentLikeService.postCommentLike(request));
    }

    @DeleteMapping("/deleteCommentLike")
    public ApiResponse<String> DeleteCommentLike(@RequestBody CommentLikeDTO request) {
        commentLikeService.deleteCommentLike(request);
        return ApiResponse.success("Like deleted.");
    }

}
