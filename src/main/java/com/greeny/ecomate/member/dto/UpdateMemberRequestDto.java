package com.greeny.ecomate.member.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateMemberRequestDto {
    @NotNull
    private String name;

    @NotNull
    private String nickname;

    @NotNull
    private String email;
}
