package com.greeny.ecomate.challenge.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateChallengeRequestDto {

    @NotNull
    private Boolean activeYn;
    @NotNull
    private String challengeTitle;
    @NotNull
    private String description;
    @NotNull
    private Long goalCnt;
    @NotNull
    private Long treePoint;

}
