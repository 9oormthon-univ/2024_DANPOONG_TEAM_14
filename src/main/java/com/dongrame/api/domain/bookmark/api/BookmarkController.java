package com.dongrame.api.domain.bookmark.api;

import com.dongrame.api.domain.bookmark.dto.BookmarkResponseDto;
import com.dongrame.api.domain.bookmark.service.BookmarkService;
import com.dongrame.api.global.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookmark")
@Tag(name = "Bookmark", description = "북마크")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @Operation(summary = "북마크 등록", description = "북마크를 등록합니다.")
    @PostMapping("/save")
    public ApiResponse<Long> saveBookmark(
        @RequestParam Long placeId
    ) {
        Long id = bookmarkService.saveBookmark(placeId);
        return ApiResponse.success(id);
    }

    @Operation(summary = "내 북마크 조회", description = "내 북마크를 조회합니다.")
    @GetMapping("/get")
    public ApiResponse<List<BookmarkResponseDto>> getBookmarks() {
        List<BookmarkResponseDto> bookmarks = bookmarkService.getBookmarks();
        return ApiResponse.success(bookmarks);
    }

    @Operation(summary = "내 북마크 삭제", description = "내 북마크를 삭제합니다.")
    @DeleteMapping("/delete")
    public ApiResponse<?> getBookmarks(
            @RequestParam List<Long> placeIds
    ) {
        bookmarkService.deleteBookmark(placeIds);
        return ApiResponse.successWithNoContent();
    }
}
