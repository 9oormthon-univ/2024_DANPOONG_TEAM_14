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
public class GetDetailReviewResponseDTO {
    private GetReviewResponseDTO review;

    private List<GetReviewCommentResponseDTO> comments;

}

