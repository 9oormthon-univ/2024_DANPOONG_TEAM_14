package com.dongrame.api.domain.review.api;


import com.dongrame.api.domain.review.dto.*;
import com.dongrame.api.domain.review.entity.Review;
import com.dongrame.api.domain.review.service.CommentLikeService;
import com.dongrame.api.domain.review.service.ReviewCommentService;
import com.dongrame.api.domain.review.service.ReviewLikeService;
import com.dongrame.api.domain.review.service.ReviewService;
import com.dongrame.api.domain.user.entity.User;
import com.dongrame.api.domain.user.service.UserService;
import com.dongrame.api.global.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
@Tag(name = "Review", description = "리뷰")
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewCommentService reviewCommentService;
    private final ReviewLikeService reviewLikeService;
    private final CommentLikeService commentLikeService;
    private final UserService userService;

    @Operation(summary = "리뷰 작성", description = "리뷰를 작성합니다.<br>" +
            "- GOOD : 편했어요<br>" +
            "- SOSO : 조금 불편했어요<br>" +
            "- BAD : 불편했어요<br>")
    @PostMapping(value = "/saveReview", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Long> postReview(
            @RequestPart @Valid PostReviewRequestDTO request,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        Review review = reviewService.saveReview(request,images);

        return ApiResponse.success(review.getId());
    }

    @Operation(summary = "가게 리뷰 리스트 조회", description = "가게 리뷰 리스트를 조회합니다.")
    @GetMapping("/getPlaceReviews")
    public ApiResponse<GetPlaceReviewResponseDTO> getPlaceReviews(@RequestParam(value = "placeId") Long placeId) {
        return ApiResponse.success(reviewService.getPlaceReviews(placeId));
    }

    @Operation(summary = "유저 리뷰 리스트 조회", description = "유저 리뷰 리스트를 조회합니다.")
    @GetMapping("/getUserReviews")
    public ApiResponse<List<GetReviewResponseDTO>> getUserReviews(@RequestParam(value = "userId") Long userId) {
        return ApiResponse.success(reviewService.getUserReviews(userId));
    }

    @Operation(summary = "내 리뷰 리스트 조회", description = "내 리뷰 리스트를 조회합니다.")
    @GetMapping("/getMYReviews")
    public ApiResponse<List<GetReviewResponseDTO>> getMYReviews() {
        return ApiResponse.success(reviewService.getMyReviews());
    }

    @Operation(summary = "리뷰 상세 조회", description = "리뷰를 상세 조회합니다.")
    @GetMapping("/getDetailReview")
    public ApiResponse<GetDetailReviewResponseDTO> getDetailReview(@RequestParam(name = "reviewId") Long request) {
        return ApiResponse.success(reviewService.getDetailReview(request));
    }

    @Operation(summary = "리뷰 수정", description = "리뷰를 수정합니다.<br>"+
            "- GOOD : 편했어요<br>" +
            "- SOSO : 조금 불편했어요<br>" +
            "- BAD : 불편했어요<br>")
    @PatchMapping(value ="/updateReview", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<PatchReviewResponseDTO> patchReview(
            @RequestPart @Valid PatchReviewRequestDTO request,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        return ApiResponse.success(PatchReviewResponseDTO.toPatchReviewResponseDTO(reviewService.updateReview(request,images)));
    }

    @Operation(summary = "리뷰 삭제", description = "리뷰를 삭제합니다.")
    @DeleteMapping("/deleteReview")
    public ApiResponse<String> deleteReview(@RequestParam(name = "reviewId") Long request) {
        User user=reviewService.deleteReview(request);
        userService.updateLevel(user);
        return ApiResponse.success("Review deleted.");
    }

    @Operation(summary = "리뷰 댓글 작성", description = "리뷰 댓글을 작성합니다.")
    @PostMapping("/saveComment")
    public ApiResponse<Long> postComment(@RequestBody @Valid PostCommentRequestDTO request) {
        return ApiResponse.success(reviewCommentService.saveReviewComment(request).getId());
    }

    @Operation(summary = "리뷰 댓글 수정", description = "리뷰 댓글을 수정합니다.")
    @PatchMapping("/updateComment")
    public ApiResponse<String> updateComment(@RequestBody @Valid PatchCommentRequestDTO request) {
        reviewCommentService.updateReviewComment(request);
        return ApiResponse.success("Comment updated.");
    }

    @Operation(summary = "리뷰 댓글 삭제", description = "리뷰 댓글을 삭제합니다.")
    @DeleteMapping("/deleteComment")
    public ApiResponse<String> deleteComment(@RequestParam(name = "commentId") Long request) {
        reviewCommentService.deleteReviewComment(request);
        return ApiResponse.success("Comment deleted.");
    }

    @Operation(summary = "리뷰 좋아요 등록", description = "리뷰 좋아요를 등록합니다.")
    @PostMapping("/saveLike")
    public ApiResponse<LikeDTO> PostLike(@RequestParam(name = "reviewId") Long request) {
        return ApiResponse.success(reviewLikeService.saveLike(request));
    }

    @Operation(summary = "리뷰 좋아요 해제", description = "리뷰 좋아요를 해제합니다.")
    @DeleteMapping("/deleteLike")
    public ApiResponse<String> DeleteLike(@RequestParam(name = "reviewId") Long request) {
        reviewLikeService.deleteLike(request);
        return ApiResponse.success("Like deleted.");
    }

    @Operation(summary = "리뷰 댓글 좋아요 등록", description = "리뷰 댓글 좋아요를 등록합니다.")
    @PostMapping("/saveCommentLike")
    public ApiResponse<CommentLikeDTO> PostCommentLike(@RequestParam(name = "commentId") Long request) {
        return ApiResponse.success(commentLikeService.saveCommentLike(request));
    }

    @Operation(summary = "리뷰 댓글 좋아요 해제", description = "리뷰 댓글 좋아요를 해제합니다.")
    @DeleteMapping("/deleteCommentLike")
    public ApiResponse<String> DeleteCommentLike(@RequestParam(name = "commentId") Long request) {
        commentLikeService.deleteCommentLike(request);
        return ApiResponse.success("Like deleted.");
    }

}
