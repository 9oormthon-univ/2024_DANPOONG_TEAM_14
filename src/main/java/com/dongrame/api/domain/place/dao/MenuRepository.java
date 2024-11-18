package com.dongrame.api.domain.place.dao;

import com.dongrame.api.domain.place.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByPlaceId(Long placeId);
}
