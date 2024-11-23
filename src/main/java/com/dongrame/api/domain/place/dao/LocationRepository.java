package com.dongrame.api.domain.place.dao;

import com.dongrame.api.domain.place.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    Location findByLatitudeAndLongitude(String latitude, String longitude);
}
