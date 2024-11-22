package com.dongrame.api.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {

    NO("성별없음"),
    MALE("남성"),
    FEMALE("여성");

    private final String description;
}
