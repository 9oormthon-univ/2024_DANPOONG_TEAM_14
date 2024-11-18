package com.dongrame.api.domain.place.dao;

import com.dongrame.api.domain.place.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}
