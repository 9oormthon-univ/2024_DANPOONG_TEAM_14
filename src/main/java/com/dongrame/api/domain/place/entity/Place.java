package com.dongrame.api.domain.place.entity;

import com.dongrame.api.domain.place.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Place extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private Float score;

    private Integer review_num;

    private String openingTime;

    private String closingTime;

    private String phoneNumber;

    private String hashtag;

    private String category;

    @OneToOne(mappedBy = "place", cascade = CascadeType.ALL)
    private Location location;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<Menu> menusList = new ArrayList<>();

}
