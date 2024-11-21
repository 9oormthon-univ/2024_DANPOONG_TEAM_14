package com.dongrame.api.domain.place.dto;

import com.dongrame.api.domain.place.entity.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LocationResponseDTO {

    private Long placeId;

    private String latitude;

    private String longitude;

    private String address;

    public static LocationResponseDTO toLocationResponseDTO(Location location){
        return LocationResponseDTO.builder()
                .placeId(location.getPlace().getId())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .build();
    }
}
