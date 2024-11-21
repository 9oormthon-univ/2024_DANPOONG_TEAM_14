package com.dongrame.api.domain.review.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCommentRequestDTO {
    private Long reviewId;

    private Long userId;

    private String comment;
}
