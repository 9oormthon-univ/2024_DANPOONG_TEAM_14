package com.dongrame.api.domain.place.api;

import com.dongrame.api.domain.place.dto.PlaceInfoResponseDTO;
import com.dongrame.api.domain.place.dto.LocationResponseDTO;
import com.dongrame.api.domain.place.dto.SearchPlaceRequestDTO;
import com.dongrame.api.domain.place.dto.SearchPlaceResponseDTO;
import com.dongrame.api.domain.place.service.PlaceService;
import com.dongrame.api.global.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/place")
@Tag(name = "Place", description = "장소")
public class PlaceController {

    private final PlaceService placeService;

    @Operation(summary = "검색 장소 등록", description = "검색한 장소를 등록합니다.")
    @PostMapping("/search")
    public ApiResponse<Long> searchPlaces(
            @RequestBody SearchPlaceRequestDTO dto
    ) {
        Long id = placeService.saveSearchPlace(dto);
        return ApiResponse.success(id);
    }

    @Operation(summary = "최근 검색 장소 조회", description = "최근에 검색한 장소를 조회합니다.")
    @GetMapping("/getSearch")
    public ApiResponse<List<SearchPlaceResponseDTO>> getSearchPlace() {
        List<SearchPlaceResponseDTO> searchPlaces = placeService.getSearchPlace();
        return ApiResponse.success(searchPlaces);
    }

    @Operation(summary = "장소 정보 조회", description = "장소 정보를 조회합니다.")
    @GetMapping("/info")
    public ApiResponse<PlaceInfoResponseDTO> getPlaceInfo(
            @RequestParam(name = "placeId") Long placeId
    ) {
        PlaceInfoResponseDTO placeInfoDto = placeService.getPlaceInfo(placeId);
        return ApiResponse.success(placeInfoDto);
    }

    @Operation(summary = "장소 위치 정보 조회", description = "장소 위치 정보를 조회합니다.")
    @GetMapping("/location")
    public ApiResponse<LocationResponseDTO> getPlaceLocation(
            @RequestParam(name = "placeId") Long placeId
    ) {
        LocationResponseDTO locationResponseDto = placeService.getLocation(placeId);
        return  ApiResponse.success(locationResponseDto);
    }
}
