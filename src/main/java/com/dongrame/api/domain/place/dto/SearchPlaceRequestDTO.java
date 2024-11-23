package com.dongrame.api.domain.place.dto;

import lombok.Getter;

@Getter
public class SearchPlaceRequestDTO {

    private String latitude;
    private String longitude;
    private String plcaeName;
    private String category;
}
