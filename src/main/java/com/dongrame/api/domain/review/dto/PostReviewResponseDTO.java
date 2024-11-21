package com.dongrame.api.domain.review.dto;

import com.dongrame.api.domain.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostReviewResponseDTO {
    private Long id;

    private String nickname;

    private String placename;

    public static PostReviewResponseDTO toReviewResponseDTO(Review requst) {
        return PostReviewResponseDTO.builder()
                .id(requst.getId())
                .nickname(requst.getUser().getNickname())
                .placename(requst.getPlace().getName())
                .build();
    }
}
