package com.greeny.ecomate.challenge.dto;

import com.greeny.ecomate.challenge.entity.Challenge;

import java.time.LocalDateTime;

public record CreateChallengeRequestDto(
        Boolean activeYn,
        String challengeTitle,
        String description,
        Long goalCnt,
        Long treePoint,
        LocalDateTime createdDate
) {

    public static CreateChallengeRequestDto of(boolean activeYn, String challengeTitle, String description, Long goalCnt, Long treePoint) {
        return new CreateChallengeRequestDto(activeYn, challengeTitle, description, goalCnt, treePoint, null);
    }

    public static CreateChallengeRequestDto from(Challenge entity) {
        return new CreateChallengeRequestDto(
                entity.getActiveYn(),
                entity.getChallengeTitle(),
                entity.getDescription(),
                entity.getGoalCnt(),
                entity.getTreePoint(),
                entity.getCreatedDate()
        );
    }

    public Challenge toEntity() {
        return Challenge.of(
                activeYn,
                challengeTitle,
                description,
                goalCnt,
                treePoint
        );
    }

}
