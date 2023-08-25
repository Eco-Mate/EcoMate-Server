package com.greeny.ecomate.board.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateSaveLogRequestDto {

    @NotNull
    private Long boardId;

}
