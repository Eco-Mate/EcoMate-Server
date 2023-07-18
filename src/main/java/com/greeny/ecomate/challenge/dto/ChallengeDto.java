package com.greeny.ecomate.challenge.dto;

import com.greeny.ecomate.challenge.entity.Challenge;

import java.time.LocalDateTime;

public record ChallengeDto(
        Long challengeId,
        Boolean activeYn,
        String challengeTitle,
        String description,
        Long goalCnt,
        Long treePoint,
        LocalDateTime createdDate
) {

    public static ChallengeDto of(boolean activeYn, String challengeTitle, String description, Long goalCnt, Long treePoint) {
        return new ChallengeDto(null, activeYn, challengeTitle, description, goalCnt, treePoint, null);
    }

    public static ChallengeDto of(boolean activeYn, String challengeTitle, String description, Long goalCnt, Long treePoint, LocalDateTime createdDate) {
        return new ChallengeDto(null, activeYn, challengeTitle, description, goalCnt, treePoint, createdDate);
    }

    public static ChallengeDto from(Challenge entity) {
        return new ChallengeDto(
                entity.getChallengeId(),
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
