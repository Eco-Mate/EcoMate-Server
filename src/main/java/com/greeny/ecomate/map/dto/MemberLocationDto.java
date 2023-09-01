package com.greeny.ecomate.map.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MemberLocationDto {

    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;

}
