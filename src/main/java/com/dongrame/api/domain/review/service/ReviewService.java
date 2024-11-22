package com.dongrame.api.domain.review.service;

import com.dongrame.api.domain.place.dao.PlaceRepository;
import com.dongrame.api.domain.place.entity.Place;
import com.dongrame.api.domain.review.dao.ReviewImagRepository;
import com.dongrame.api.domain.review.dao.ReviewLikeRepository;
import com.dongrame.api.domain.review.dao.ReviewRepository;
import com.dongrame.api.domain.review.dto.GetReviewResponseDTO;
import com.dongrame.api.domain.review.dto.PatchReviewRequestDTO;
import com.dongrame.api.domain.review.dto.PostReviewRequestDTO;
import com.dongrame.api.domain.review.entity.Review;
import com.dongrame.api.domain.review.entity.ReviewImag;
import com.dongrame.api.domain.review.entity.ReviewLike;
import com.dongrame.api.domain.user.entity.User;
import com.dongrame.api.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewImagRepository imagRepository;
    private final PlaceRepository placeRepository;
    private final ReviewLikeRepository reviewLikeRepository;

    private final ReviewImagService reviewImagService;
    private final UserService userService;



    @Transactional
    public Review saveReview(PostReviewRequestDTO request,List<MultipartFile> images) {
        Place place = placeRepository.findById(request.getPlaceId()).orElseThrow(()->new RuntimeException("찾을 수 없습니다"));
        User currentUser=userService.getCurrentUser();
        Review newReview = Review.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .score(request.getScore())
                .place(place)
                .user(currentUser)
                .build();
        reviewRepository.save(newReview);
        if (images != null) {
            validateImages(images);
            reviewImagService.saveReviewImag(newReview.getId(),images);
        }
        return newReview;
    }

    @Transactional
    public GetReviewResponseDTO getReview(Long reviewId) {
        Review review=reviewRepository.findById(reviewId).orElseThrow(()->new RuntimeException("찾을 수 없습니다"));
        ReviewLike reviewLike=reviewLikeRepository.findByReviewAndUser(review,userService.getCurrentUser());
        boolean liked=true;
        if(reviewLike==null){
            liked=false;
        }
        List<String> imageUrl= imagRepository.findByReviewId(reviewId).stream()
                .map(ReviewImag::getImageUrl)
                .collect(Collectors.toList());
        return GetReviewResponseDTO.builder()
                .userId(review.getUser().getId())
                .placeName(review.getPlace().getName())
                .title(review.getTitle())
                .content(review.getContent())
                .score(review.getScore())
                .likeNum(review.getLikeNum())
                .liked(liked)
                .imageUrl(imageUrl)
                .build();
    }

    @Transactional
    public Review updateReview(PatchReviewRequestDTO request,List<MultipartFile> images){
        Review saveReview = reviewRepository.findById(request.getReviewId()).orElseThrow(()->new RuntimeException("찾을 수 없습니다"));
        if(!saveReview.getUser().equals(userService.getCurrentUser())){
            throw new RuntimeException("권한이 없습니다");
        }

        saveReview.setTitle(request.getTitle());

        saveReview.setContent(request.getContent());

        saveReview.setScore(request.getScore());

        reviewImagService.deleteReviewImages(request.getReviewId());
        if(images != null ){
            validateImages(images);
            reviewImagService.saveReviewImag(request.getReviewId(),images);
        }
        return reviewRepository.save(saveReview);
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        Review delReview=reviewRepository.findById(reviewId).orElseThrow(()->new RuntimeException("찾을 수 없습니다"));
        if(!delReview.getUser().equals(userService.getCurrentUser())){
            throw new RuntimeException("권한이 없습니다");
        }
        reviewImagService.deleteReviewImages(reviewId);
        reviewRepository.delete(delReview);
    }

    // 이미지 리스트의 유효성을 검사하는 메소드
    private void validateImages(List<MultipartFile> images) {
        if (images != null) {
            for (MultipartFile image : images) {
                // 파일 이름에서 확장자 추출
                String extension = getFileExtension(image.getOriginalFilename());
                if (!isValidImageExtension(extension)) {
                    throw new RuntimeException("유효한 이미지 파일이 아닙니다: " + image.getOriginalFilename());
                }
            }
        }
    }

    // 파일 이름에서 확장자를 추출하는 메소드
    private String getFileExtension(String filename) {
        if (filename == null || filename.isEmpty()) {
            return "";
        }
        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == filename.length() - 1) {
            return ""; // 확장자가 없는 경우
        }
        return filename.substring(lastDotIndex + 1).toLowerCase(); // 소문자로 변환하여 비교
    }

    // 유효한 이미지 확장자를 확인하는 메소드
    private boolean isValidImageExtension(String extension) {
        return extension != null && (extension.equals("jpg") ||
                extension.equals("jpeg") ||
                extension.equals("png") ||
                extension.equals("gif") ||
                extension.equals("bmp"));
    }
}
