package com.dongrame.api.domain.place.dto;

import com.dongrame.api.domain.place.entity.Place;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InfoResponseDTO {
    private Long placeId;

    private String name;

    private Float score;

    private Integer review_num;

    private String openingTime;

    private String closingTime;

    private String phoneNumber;

    private String hashtag;

    private String category;

    public static InfoResponseDTO toInfoResponseDTO(Place place){
        return InfoResponseDTO.builder()
                .placeId(place.getId())
                .name(place.getName())
                .score(place.getScore())
                .review_num(place.getReview_num())
                .openingTime(place.getOpeningTime())
                .closingTime(place.getClosingTime())
                .phoneNumber(place.getPhoneNumber())
                .hashtag(place.getHashtag())
                .category(place.getCategory())
                .build();
    }
}
