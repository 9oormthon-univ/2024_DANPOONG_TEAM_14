package com.dongrame.api.domain.review.dto;

import com.dongrame.api.domain.user.entity.UserType;
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

    private String userName;

    private UserType userType;

    private String ProfileImageUrl;

    private Integer likeNum;

    private String content;

    private boolean liked;
}
