package com.dongrame.api.domain.place.entity;

import com.dongrame.api.domain.place.dto.SearchPlaceRequestDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Location {

    @Id
    private Long id; // 부모의 ID와 동일하게 설정

    @OneToOne
    @MapsId // 자식의 ID가 부모의 ID와 같다는 것을 나타냄
    @JoinColumn(name = "placeId")
    private Place place;

    @Column(nullable = false)
    private String latitude;

    @Column(nullable = false)
    private String longitude;

    public static Location toLocation(SearchPlaceRequestDTO dto, Place place) {
        return Location.builder()
                .place(place)
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .build();
    }
}
