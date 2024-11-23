package com.dongrame.api.domain.place.entity;

import com.dongrame.api.domain.place.dto.SearchPlaceRequestDTO;
import com.dongrame.api.domain.place.entity.base.BaseEntity;
import com.dongrame.api.domain.review.entity.Review;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Place extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private Integer GOOD;

    private Integer SOSO;

    private Integer BAD;

    private Integer reviewNum;

    private String hashtag;

    private String category;

    @OneToOne(mappedBy = "place", cascade = CascadeType.ALL)
    private Location location;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<Menu> menus;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    public static Place toPlace(SearchPlaceRequestDTO dto) {
        return Place.builder()
                .name(dto.getPlcaeName())
                .reviewNum(0)
                .GOOD(0)
                .SOSO(0)
                .BAD(0)
                .category(dto.getCategory())
                .build();
    }

    public void updateLocation(Location location) {
        this.location = location;
    }
}
