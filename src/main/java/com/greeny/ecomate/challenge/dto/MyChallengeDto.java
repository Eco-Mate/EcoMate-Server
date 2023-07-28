package com.greeny.ecomate.challenge.dto;

import com.greeny.ecomate.challenge.entity.AchieveType;
import com.greeny.ecomate.challenge.entity.MyChallenge;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record MyChallengeDto(
        Long myChallengeId,
        String nickname,
        Long challengeId,
        AchieveType achieveType,
        Long achieveCnt,
        Long achievePoint,
        Long doneCnt
) {

    public static MyChallengeDto of(Long myChallengeId, String nickname, Long challengeId, AchieveType achieveType, Long achieveCnt, Long achievePoint, Long doneCnt) {
        return new MyChallengeDto(myChallengeId, nickname, challengeId, achieveType, achieveCnt, achievePoint, doneCnt);
    }

    public static MyChallengeDto from(MyChallenge entity) {
        return new MyChallengeDto(
                entity.getMyChallengeId(),
                entity.getMember().getNickname(),
                entity.getChallenge().getChallengeId(),
                entity.getAchieveType(),
                entity.getAchieveCnt(),
                entity.getAchievePoint(),
                entity.getDoneCnt());
    }

}
