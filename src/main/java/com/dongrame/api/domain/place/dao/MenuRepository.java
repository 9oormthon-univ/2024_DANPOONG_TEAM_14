package com.dongrame.api.domain.place.dao;

import com.dongrame.api.domain.place.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByPlaceId(Long placeId);
}
