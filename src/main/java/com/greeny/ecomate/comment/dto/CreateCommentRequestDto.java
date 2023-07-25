package com.greeny.ecomate.comment.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateCommentRequestDto {
    @NotNull
    private Long boardId;

    @NotNull
    private String nickname;

    @NotNull
    private String content;
}
