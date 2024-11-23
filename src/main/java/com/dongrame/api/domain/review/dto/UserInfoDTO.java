package com.dongrame.api.domain.review.dto;

import com.dongrame.api.domain.user.entity.User;
import com.dongrame.api.domain.user.entity.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
    private Long userId;

    private String userName;

    private UserType userType;

    private String ProfileImageUrl;

    public static UserInfoDTO toUserInfoDTO(User user) {
        return UserInfoDTO.builder()
                .userId(user.getId())
                .userName(user.getNickname())
                .userType(user.getUserType())
                .ProfileImageUrl(user.getProfileImage())
                .build();
    }
}
