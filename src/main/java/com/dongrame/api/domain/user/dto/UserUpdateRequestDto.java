package com.dongrame.api.domain.user.dto;

import com.dongrame.api.domain.user.entity.UserType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserUpdateRequestDto {

    private String username;
    private String email;
    private UserType userType;
}
