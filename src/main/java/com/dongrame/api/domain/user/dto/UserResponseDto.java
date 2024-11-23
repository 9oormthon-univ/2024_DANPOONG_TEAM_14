package com.dongrame.api.domain.user.dto;

import com.dongrame.api.domain.user.entity.User;
import com.dongrame.api.domain.user.entity.UserType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponseDto {

    private String kakaoId;
    private String username;
    private String email;
    private boolean isProfileImageUpdated;
    private String profileImageUrl;
    private UserType userType;
    private int level;
    private int reviewNum;
    private int bookmarkNum;

    public static UserResponseDto toDto(User user) {
        return UserResponseDto.builder()
                .kakaoId(user.getKakaoId())
                .username(user.getNickname())
                .email(user.getEmail())
                .isProfileImageUpdated(user.isProfileImageUpdated())
                .profileImageUrl(user.getProfileImage())
                .userType(user.getUserType())
                .level(user.getLevel())
                .reviewNum(user.getReviews().size())
                .bookmarkNum(user.getBookmarks().size())
                .build();
    }
}
