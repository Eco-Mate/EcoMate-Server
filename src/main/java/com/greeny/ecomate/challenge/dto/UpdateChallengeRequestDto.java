package com.greeny.ecomate.challenge.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateChallengeRequestDto {

    @NotNull
    private String challengeTitle;
    @NotNull
    private String description;
    @NotNull
    private Long goalCnt;
    @NotNull
    private Long treePoint;

}
