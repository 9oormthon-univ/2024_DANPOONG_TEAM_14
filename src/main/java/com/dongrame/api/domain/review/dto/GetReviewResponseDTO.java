package com.dongrame.api.domain.review.dto;

import com.dongrame.api.domain.review.entity.Score;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetReviewResponseDTO {
    private Long reviewId;

    private String title;

    private String content;

    private Score score;

    private Integer likeNum;

    private Integer commentNum;

    private UserInfoDTO userInfo;

    private String placeName;

    private boolean liked;

    private List<String> imageUrl;

}

