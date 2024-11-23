package com.dongrame.api.domain.place.dto;

import com.dongrame.api.domain.place.entity.Place;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SearchPlaceResponseDTO {

    private Long placeId;
    private String placeName;
    private String latitude;
    private String longitude;

    public static SearchPlaceResponseDTO toSearchPlaceResponseDTO(Place place) {
        return SearchPlaceResponseDTO.builder()
                .placeId(place.getId())
                .placeName(place.getName())
                .latitude(place.getLocation().getLatitude())
                .longitude(place.getLocation().getLongitude())
                .build();
    }
}
