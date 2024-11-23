package com.dongrame.api.domain.user.dto;

import com.dongrame.api.domain.user.entity.Gender;
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
    private UserType userType;
    private int age;
    private Gender gender;
    private int reviewNum;
    private int bookmarkNum;

    public static UserResponseDto toDto(User user) {
        return UserResponseDto.builder()
                .kakaoId(user.getKakaoId())
                .username(user.getNickname())
                .email(user.getEmail())
                .userType(user.getUserType())
                .age(user.getAge())
                .gender(user.getGender())
                .reviewNum(user.getReviews().size())
                .bookmarkNum(user.getBookmarks().size())
                .build();
    }
}
