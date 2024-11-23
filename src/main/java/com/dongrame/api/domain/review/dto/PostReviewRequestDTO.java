package com.dongrame.api.domain.review.dto;

import com.dongrame.api.domain.review.entity.Score;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostReviewRequestDTO {

    @NotNull
    private Long placeId;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private Score score;
}
