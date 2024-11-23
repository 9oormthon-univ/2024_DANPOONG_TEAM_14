package com.dongrame.api.domain.place.dao;

import com.dongrame.api.domain.place.entity.Place;
import com.dongrame.api.domain.place.entity.Search;
import com.dongrame.api.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRepository extends JpaRepository<Search, Long> {

    List<Search> findByUserOrderByCreatedAtDesc(User user);

    Search findByUserAndPlace(User user, Place place);
}
