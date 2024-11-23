package com.dongrame.api.domain.bookmark.dto;

import com.dongrame.api.domain.bookmark.entity.Bookmark;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookmarkResponseDto {

    private Long placeId;
    private String placeName;
    private String placeCategory;

    public static BookmarkResponseDto toBookmarkResponseDto(Bookmark bookmark) {
        return BookmarkResponseDto.builder()
                .placeId(bookmark.getPlace().getId())
                .placeName(bookmark.getPlace().getName())
                .placeCategory(bookmark.getPlace().getCategory())
                .build();
    }
}
