package com.dongrame.api.domain.place.dto;

import com.dongrame.api.domain.place.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuResponseDTO {
    private Long placeId;

    private List<Menu> menus;

    public static MenuResponseDTO toMenuResponsetDTO(List<Menu> menus){
        Long id = menus.get(0).getPlace().getId();
        List<Menu> menuList =menus.stream().map(menu -> Menu.builder()
                        .id(menu.getId())
                        .url(menu.getUrl())
                        .name(menu.getName())
                        .price(menu.getPrice())
                        .build())
                .collect(Collectors.toList());
        return MenuResponseDTO.builder()
                .placeId(id)
                .menus(menuList)
                .build();
    }
}
