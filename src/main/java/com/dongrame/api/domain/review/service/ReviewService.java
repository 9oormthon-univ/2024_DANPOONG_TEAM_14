package com.dongrame.api.domain.review.service;

import com.dongrame.api.domain.place.dao.PlaceRepository;
import com.dongrame.api.domain.place.entity.Place;
import com.dongrame.api.domain.review.dao.ReviewImagRepository;
import com.dongrame.api.domain.review.dao.ReviewRepository;
import com.dongrame.api.domain.review.dto.GetReviewResponseDTO;
import com.dongrame.api.domain.review.dto.PatchReviewRequestDTO;
import com.dongrame.api.domain.review.dto.PostReviewRequestDTO;
import com.dongrame.api.domain.review.entity.Review;
import com.dongrame.api.domain.review.entity.ReviewImag;
import com.dongrame.api.domain.user.dao.UserRepository;
import com.dongrame.api.domain.user.entity.User;
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
    private final UserRepository userRepository;

    private final ReviewImagService reviewImagService;



    @Transactional
    public Review postReview(PostReviewRequestDTO request) {
        Place place = placeRepository.findById(request.getPlaceId()).orElseThrow(()->new RuntimeException("찾을 수 없습니다"));
        User user = userRepository.findById(request.getUserId()).orElseThrow(()->new RuntimeException("찾을 수 없습니다"));
        Review newReview = Review.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .score(request.getScore())
                .place(place)
                .user(user)
                .build();
        reviewRepository.save(newReview);
        if (request.getImages() != null) {
            reviewImagService.postReviewImag(newReview.getId(),request.getImages());
        }
        return newReview;
    }

    @Transactional
    public GetReviewResponseDTO getReview(Long reviewId) {
        Review review=reviewRepository.findById(reviewId).orElseThrow(()->new RuntimeException("찾을 수 없습니다"));
        List<String> imageUrl= imagRepository.findByReviewId(reviewId).stream()
                .map(ReviewImag::getImageUrl) // ReviewImage 객체에서 URL 추출
                .collect(Collectors.toList());
        return GetReviewResponseDTO.builder()
                .userId(review.getUser().getId())
                .placeName(review.getPlace().getName())
                .title(review.getTitle())
                .content(review.getContent())
                .score(review.getScore())
                .likeNum(review.getLikeNum())
                .imageUrl(imageUrl)
                .build();
    }

    @Transactional
    public Review patchReview(PatchReviewRequestDTO request){
        Review saveReview = reviewRepository.findById(request.getReviewId()).orElseThrow(()->new RuntimeException("찾을 수 없습니다"));
        if(request.getTitle()!=null){
            saveReview.setTitle(request.getTitle());
        }
        if(request.getContent()!=null){
            saveReview.setContent(request.getContent());
        }
        if(request.getScore()!=null){
            saveReview.setScore(request.getScore());
        }
        List<MultipartFile> images=request.getImages();
        if(images!=null){
            reviewImagService.deleteReviewImages(request.getReviewId());
            reviewImagService.postReviewImag(request.getReviewId(),request.getImages());
        }
        return reviewRepository.save(saveReview);
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        Review delReview=reviewRepository.findById(reviewId).orElseThrow(()->new RuntimeException("찾을 수 없습니다"));
        reviewImagService.deleteReviewImages(reviewId);
        reviewRepository.delete(delReview);
    }

}
