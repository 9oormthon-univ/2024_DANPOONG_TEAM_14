package com.dongrame.api.domain.user.service;

import com.dongrame.api.domain.place.dao.PlaceRepository;
import com.dongrame.api.domain.place.entity.Place;
import com.dongrame.api.domain.review.dao.CommentLikeRepository;
import com.dongrame.api.domain.review.dao.ReviewCommentRepository;
import com.dongrame.api.domain.review.dao.ReviewLikeRepository;
import com.dongrame.api.domain.review.dao.ReviewRepository;
import com.dongrame.api.domain.review.entity.*;
import com.dongrame.api.domain.user.dao.UserRepository;
import com.dongrame.api.domain.user.dto.UserResponseDto;
import com.dongrame.api.domain.user.dto.UserUpdateRequestDto;
import com.dongrame.api.domain.user.entity.User;
import com.dongrame.api.domain.user.entity.UserType;
import com.dongrame.api.global.auth.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final PlaceRepository placeRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewCommentRepository reviewCommentRepository;
    private final ReviewLikeRepository reviewLikeRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public Long save(String kakaoId, String nickname, String profileImage, String email) {


        User newUser = User.builder()
                .active(true)
                .kakaoId(kakaoId)
                .nickname(nickname)
                .profileImage(profileImage)
                .email(email)
                .level(1)
                .build();

        return userRepository.save(newUser).getId();
    }

    @Transactional
    public void activeState(String kakaoId, String nickname, String profileImage, String email) {
        Optional<User> existingUser = userRepository.findByKakaoIdAndActiveFalse(kakaoId);

        if (existingUser.isPresent() && !existingUser.get().isActive()) {
            User user = existingUser.get();
            user.setActive(true);
            user.setNickname(nickname);
            user.setProfileImage(profileImage);
            user.setEmail(email);

            //유저 삭제시 처리/////////////////////////////////////////////
            List<Review> reviews= reviewRepository.findByUserIdAndUserActiveTrue(user.getId());
            List<ReviewComment> reviewComments =reviewCommentRepository.findByUserIdAndUserActiveTrue(user.getId());
            List<ReviewLike> reviewLikes=reviewLikeRepository.findByUser(user);
            List<CommentLike> commentLikes=commentLikeRepository.findByUser(user);

            for(Review review:reviews){
                Place place=review.getPlace();
                Score score= review.getScore();
                if(score== Score.GOOD){
                    place.setGOOD(place.getGOOD()+1);
                }
                else if(score== Score.SOSO){
                    place.setSOSO(place.getSOSO()+1);
                }
                else if(score== Score.BAD){
                    place.setBAD(place.getBAD()+1);
                }
                place.setReviewNum(place.getReviewNum()+1);
                placeRepository.save(place);
            }

            for(ReviewComment reviewComment:reviewComments){
                Review review=reviewComment.getReview();
                review.setCommentNum(review.getCommentNum()+1);
                reviewRepository.save(review);
            }

            for(ReviewLike reviewLike:reviewLikes){
                Review review=reviewLike.getReview();
                review.setLikeNum(review.getLikeNum()+1);
                reviewRepository.save(review);
            }

            for(CommentLike commentLike:commentLikes){
                ReviewComment reviewComment=commentLike.getReviewComment();
                reviewComment.setLikeNum(reviewComment.getLikeNum()+1);
                reviewCommentRepository.save(reviewComment);
            }
            ////////////////////////////////////////////////////////////////////
            userRepository.save(user);
        }

    }

    @Transactional
    public Long updateUserType(UserType type) {
        User user = getCurrentUser();
        user.updateUserType(type);
        return user.getId();
    }

    @Transactional
    public Long updateUser(UserUpdateRequestDto dto) {
        User user = getCurrentUser();
        user.update(dto);
        return user.getId();
    }

    @Transactional
    public void updateLevel(User user){
        user.updateLevel();
    }

    @Transactional
    public String updateProfileImage(MultipartFile profileImage) {
        try {
            User currentUser = getCurrentUser();
            String uploadDir = System.getProperty("user.home") + "/uploads/profileImages";
            Path directory = Paths.get(uploadDir);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }
            String originalFilename = profileImage.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = "profile_" + currentUser.getKakaoId() + "_" + System.currentTimeMillis() + extension;
            Path filePath = directory.resolve(newFilename);
            profileImage.transferTo(filePath.toFile());

            String profileImagePath = "/uploads/profileImages/" + newFilename;
            currentUser.updateProfileImage(profileImagePath);

            return profileImagePath;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save profile image", e);
        }
    }

    @Transactional
    public Long deleteUser() {
        User user = getCurrentUser();

        //유저 삭제시 처리/////////////////////////////////////////////
        List<Review> reviews= reviewRepository.findByUserIdAndUserActiveTrue(user.getId());
        List<ReviewComment> reviewComments =reviewCommentRepository.findByUserIdAndUserActiveTrue(user.getId());
        List<ReviewLike> reviewLikes=reviewLikeRepository.findByUser(user);
        List<CommentLike> commentLikes=commentLikeRepository.findByUser(user);

        for(Review review:reviews){
            Place place=review.getPlace();
            Score score= review.getScore();
            if(score== Score.GOOD){
                place.setGOOD(place.getGOOD()-1);
            }
            else if(score== Score.SOSO){
                place.setSOSO(place.getSOSO()-1);
            }
            else if(score== Score.BAD){
                place.setBAD(place.getBAD()-1);
            }
            place.setReviewNum(place.getReviewNum()-1);
            placeRepository.save(place);
        }

        for(ReviewComment reviewComment:reviewComments){
            Review review=reviewComment.getReview();
            review.setCommentNum(review.getCommentNum()-1);
            reviewRepository.save(review);
        }

        for(ReviewLike reviewLike:reviewLikes){
            Review review=reviewLike.getReview();
            review.setLikeNum(review.getLikeNum()-1);
            reviewRepository.save(review);
        }

        for(CommentLike commentLike:commentLikes){
            ReviewComment reviewComment=commentLike.getReviewComment();
            reviewComment.setLikeNum(reviewComment.getLikeNum()-1);
            reviewCommentRepository.save(reviewComment);
        }
        ///////////////////////////////////////////////////////


        user.setActive(false);
        SecurityContextHolder.clearContext();
        refreshTokenService.deleteRefreshToken(user.getKakaoId());
        return user.getId();
    }

    public User findByKakaoId(String kakaoId) {
        return userRepository.findByKakaoId(kakaoId).orElse(null);
    }

    public UserResponseDto getCurrentLoginUser() {
        User user = getCurrentUser();
        return UserResponseDto.toDto(user);
    }

    public User getCurrentUser() {
        String kakaoId = getCurrentUserId();
        return userRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }

    public String getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof OAuth2User) {
            return String.valueOf(((OAuth2User) principal).getAttributes().get("id"));
        } else {
            return principal.toString();
        }
    }
}
