package com.greeny.ecomate.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class UpdateBoardRequestDto {
    // image, challenge 는 수정 불가

    @NotNull
    private String boardTitle;

    @NotNull
    private String boardContent;

}
