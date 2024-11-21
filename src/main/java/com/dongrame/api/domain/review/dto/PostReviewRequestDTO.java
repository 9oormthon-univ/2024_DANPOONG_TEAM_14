package com.dongrame.api.domain.review.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class PostReviewRequestDTO {
    private String title;

    private String content;

    private Float score;

    private Long placeId;

    private Long userId;

    private List<MultipartFile> images;

}
