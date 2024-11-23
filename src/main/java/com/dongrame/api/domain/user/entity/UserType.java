package com.dongrame.api.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserType {

    DISABLED("장애인"),
    ASSISTANCE_DOG("보조견"),
    ELDERLY("노약자"),
    CHILD("어린이");

    private final String name;
}
