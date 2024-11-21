package com.dongrame.api.domain.review.dto;

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

    private String title;

    private String content;

    private Float score;

    private Integer likeNum;

    private Long userId;

    private String placeName;

    private List<String> imageUrl;

}

