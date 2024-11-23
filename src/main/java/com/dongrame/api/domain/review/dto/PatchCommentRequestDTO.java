package com.dongrame.api.domain.review.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatchCommentRequestDTO {
    @NotNull
    private Long commentId;

    @NotNull
    private String comment;
}
