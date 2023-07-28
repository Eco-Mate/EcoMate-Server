package com.greeny.ecomate.posting.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateBoardRequestDto {
    // image, challenge 는 수정 불가

    @NotNull
    private String boardTitle;

    @NotNull
    private String boardContent;

}
