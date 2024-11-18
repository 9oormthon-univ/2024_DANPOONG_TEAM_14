package com.dongrame.api.domain.place.api;



import com.dongrame.api.domain.place.dto.InfoResponseDTO;
import com.dongrame.api.domain.place.dto.LocationResponseDTO;
import com.dongrame.api.domain.place.dto.MenuResponseDTO;
import com.dongrame.api.domain.place.entity.Place;
import com.dongrame.api.domain.place.service.PlaceService;
import com.dongrame.api.global.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/places")
public class PlaceController {
    private final PlaceService placeCommandService;

    @GetMapping("/info")
    public ApiResponse<InfoResponseDTO> getPlaceInfo(@RequestParam(name = "placeId") Long request) {
        Place place=placeCommandService.getPlaceInfo(request);
        return ApiResponse.success(InfoResponseDTO.toInfoResponseDTO(place));
    }

    @GetMapping("/menu")
    public ApiResponse<MenuResponseDTO> getPlaceMenu(@RequestParam(name = "placeId") Long request) {
        return  ApiResponse.success(MenuResponseDTO.toMenuResponsetDTO(placeCommandService.getMenus(request)));
    }

    @GetMapping("/location")
    public ApiResponse<LocationResponseDTO> getPlaceLocation(@RequestParam(name = "placeId") Long request) {
        return  ApiResponse.success(LocationResponseDTO.toLocationResponseDTO(placeCommandService.getLocation(request)));
    }

}
