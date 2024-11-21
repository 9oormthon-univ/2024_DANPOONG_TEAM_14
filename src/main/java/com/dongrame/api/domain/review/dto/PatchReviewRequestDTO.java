package com.dongrame.api.domain.review.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class PatchReviewRequestDTO {
    private Long reviewId;

    private String title;

    private String content;

    private Float score;

    private List<MultipartFile> images;

}
