package com.dongrame.api.domain.review.dto;

import com.dongrame.api.domain.review.entity.Review;
import com.dongrame.api.domain.review.entity.Score;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PatchReviewResponseDTO {
    private Long reviewid;

    private String title;

    private String content;

    private Score score;

    public static PatchReviewResponseDTO toPatchReviewResponseDTO(Review requst) {
        return PatchReviewResponseDTO.builder()
                .reviewid(requst.getId())
                .title(requst.getTitle())
                .content(requst.getContent())
                .score(requst.getScore())
                .build();
    }
}
