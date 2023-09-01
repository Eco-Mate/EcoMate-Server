package com.greeny.ecomate.member.dto;

import lombok.Data;

@Data
public class LevelDto {

    private String levelName;
    private Long goalTreePoint;
    private String nextLevelName;

    public LevelDto(String levelName, Long goalTreePoint, String nextLevelName) {
        this.levelName = levelName;
        this.goalTreePoint = goalTreePoint;
        this.nextLevelName = nextLevelName;
    }

}
