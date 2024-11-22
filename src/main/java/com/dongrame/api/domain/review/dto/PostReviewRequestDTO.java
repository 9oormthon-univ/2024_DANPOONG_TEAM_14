package com.dongrame.api.domain.review.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostReviewRequestDTO {
    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private Float score;

    @NotNull
    private Long placeId;

}
