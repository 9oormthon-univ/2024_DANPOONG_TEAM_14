package com.dongrame.api.domain.bookmark.service;

import com.dongrame.api.domain.bookmark.dao.BookmarkRepository;
import com.dongrame.api.domain.bookmark.dto.BookmarkResponseDto;
import com.dongrame.api.domain.bookmark.entity.Bookmark;
import com.dongrame.api.domain.place.dao.PlaceRepository;
import com.dongrame.api.domain.place.entity.Place;
import com.dongrame.api.domain.place.service.PlaceService;
import com.dongrame.api.domain.user.entity.User;
import com.dongrame.api.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final PlaceRepository placeRepository;
    private final UserService userService;

    @Transactional
    public Long saveBookmark(Long placeId) {
        User currentUser = userService.getCurrentUser();
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new RuntimeException("해당 장소가 없습니다."));
        if(bookmarkRepository.findByUserAndPlace(currentUser, place).isPresent()) {
            throw new RuntimeException("이미 북마크된 장소입니다.");
        }
        Bookmark bookmark = Bookmark.toBookmark(currentUser, place);
        bookmarkRepository.save(bookmark);
        return bookmark.getId();
    }

    public List<BookmarkResponseDto> getBookmarks() {
        User currentUser = userService.getCurrentUser();
        List<Bookmark> bookmarks = bookmarkRepository.findByUserOrderByCreatedAtDesc(currentUser);
        if(bookmarks.isEmpty()) {
            return null;
        }
        return bookmarks.stream()
                .map(BookmarkResponseDto::toBookmarkResponseDto)
                .toList();
    }

    @Transactional
    public Long deleteBookmark(Long placeId) {
        User currentUser = userService.getCurrentUser();
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new RuntimeException("해당 장소는 없습니다."));
        Bookmark bookmark = bookmarkRepository.findByUserAndPlace(currentUser, place)
                .orElseThrow(() -> new RuntimeException("해당 북마크는 존재하지 않습니다."));
        bookmarkRepository.delete(bookmark);
        return bookmark.getId();
    }
}