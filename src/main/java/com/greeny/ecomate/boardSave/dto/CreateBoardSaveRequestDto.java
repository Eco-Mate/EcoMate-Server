package com.greeny.ecomate.boardSave.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateBoardSaveRequestDto {

    @NotNull
    private Long boardId;

}
