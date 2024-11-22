package com.dongrame.api.domain.bookmark.dao;

import com.dongrame.api.domain.bookmark.entity.Bookmark;
import com.dongrame.api.domain.place.entity.Place;
import com.dongrame.api.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    List<Bookmark> findByUserOrderByCreatedAtDesc(User user);

    Optional<Bookmark> findByUserAndPlace(User user, Place place);
}
