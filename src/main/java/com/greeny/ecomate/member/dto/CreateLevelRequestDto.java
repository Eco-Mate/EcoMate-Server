package com.greeny.ecomate.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateLevelRequestDto {

    @NotNull
    private String levelName;
    @NotNull
    private Long goalTreePoint;

}
