package com.dongrame.api.domain.review.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatchReviewRequestDTO {

    @NotNull(message = "리뷰 ID는 필수입니다.")
    private Long reviewId;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private Float score;

}
