package com.greeny.ecomate.like.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateLikeDto {

    @NotNull
    private Long boardId;
}
