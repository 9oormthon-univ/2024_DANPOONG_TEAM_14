package com.dongrame.api.domain.place.service;


import com.dongrame.api.domain.place.dao.LocationRepository;
import com.dongrame.api.domain.place.dao.MenuRepository;
import com.dongrame.api.domain.place.dao.PlaceRepository;
import com.dongrame.api.domain.place.entity.Location;
import com.dongrame.api.domain.place.entity.Menu;
import com.dongrame.api.domain.place.entity.Place;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final MenuRepository menuRepository;
    private final LocationRepository locationRepository;

    @Transactional
    public Place getPlaceInfo(Long request){
        return placeRepository.findById(request).orElseThrow(()->new RuntimeException("찾을 수 없습니다"));
    }

    @Transactional
    public List<Menu> getMenus(Long request) {
        List<Menu> menus = menuRepository.findByPlaceId(request);

        // 메뉴가 존재하지 않을 경우 예외를 발생시킴
        if (menus.isEmpty()) {
            throw new RuntimeException("찾을 수 없습니다");
        }

        return menus; // 존재할 경우 메뉴 리스트 반환
    }

    @Transactional
    public Location getLocation(Long request) {
        return locationRepository.findById(request).orElseThrow(()->new RuntimeException("찾을 수 없습니다"));
    }
}

