package com.greeny.ecomate.posting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class CreateBoardRequestDto {

    @NotNull
    private String nickname;

    @NotNull
    private Long challengeId;

    @NotNull
    private String boardTitle;

    @NotNull
    private String boardContent;

}
