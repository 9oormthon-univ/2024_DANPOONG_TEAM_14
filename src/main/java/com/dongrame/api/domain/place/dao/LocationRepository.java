package com.dongrame.api.domain.place.dao;

import com.dongrame.api.domain.place.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
