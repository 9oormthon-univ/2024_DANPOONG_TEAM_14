package com.dongrame.api.domain.place.dto;

import com.dongrame.api.domain.place.entity.Place;
import com.dongrame.api.domain.review.entity.Score;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceInfoResponseDTO {

    private Long placeId;

    private String name;

    private Score score;

    private Integer reviewNum;

    private String category;

    private boolean isBookmarked;

    public static PlaceInfoResponseDTO toInfoResponseDTO(Place place, boolean isBookmarked, Score score) {
        return PlaceInfoResponseDTO.builder()
                .placeId(place.getId())
                .name(place.getName())
                .score(score)
                .reviewNum(place.getReviewNum())
                .category(place.getCategory())
                .isBookmarked(isBookmarked)
                .build();
    }
}
