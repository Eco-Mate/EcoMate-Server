package com.greeny.ecomate.member.dto;

import lombok.Data;

@Data
public class UpdateLevelRequestDto {

    private String levelName;
    private Long goalTreePoint;

}
