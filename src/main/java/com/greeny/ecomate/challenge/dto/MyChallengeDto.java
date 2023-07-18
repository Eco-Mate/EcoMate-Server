package com.greeny.ecomate.challenge.dto;

import com.greeny.ecomate.challenge.entity.AchieveType;
import com.greeny.ecomate.challenge.entity.MyChallenge;

public record MyChallengeDto(
        Long myChallengeId,
        String nickname,
        Long challengeId,
        AchieveType achieveType,
        Long achieveCnt,
        Long achievePoint
) {

    public static MyChallengeDto of(Long myChallengeId, String nickname, Long challengeId, AchieveType achieveType, Long achieveCnt, Long achievePoint) {
        return new MyChallengeDto(myChallengeId, nickname, challengeId, achieveType, achieveCnt, achievePoint);
    }

    public static MyChallengeDto from(MyChallenge entity) {
        return new MyChallengeDto(
                entity.getMyChallengeId(),
                entity.getUser().getNickname(),
                entity.getChallenge().getChallengeId(),
                entity.getAchieveType(),
                entity.getAchieveCnt(),
                entity.getAchievePoint());
    }

}
