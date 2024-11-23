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
public class GetPlaceReviewResponseDTO {
    private List<GetReviewResponseDTO> reviews;

    private String placeName;

    private Integer reviewNum;

    private Integer GOOD;

    private Integer SOSO;

    private Integer BAD;
}
