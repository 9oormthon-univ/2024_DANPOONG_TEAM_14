package com.dongrame.api.domain.review.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCommentRequestDTO {
    @NotNull
    private Long reviewId;

    @NotNull
    private String comment;
}
