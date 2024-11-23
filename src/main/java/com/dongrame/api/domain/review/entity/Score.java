package com.dongrame.api.domain.review.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Score {
    GOOD("편했어요"),
    SOSO("조금 불편했어요"),
    BAD("불편했어요");

    private final String score;
}
