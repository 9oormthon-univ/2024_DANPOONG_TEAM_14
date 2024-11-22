package com.dongrame.api.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetReviewCommentResponseDTO {
    private Long commentId;

    private Long userId;

    private Integer likeNum;

    private String content;

    private boolean liked;
}
