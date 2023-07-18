package com.greeny.ecomate.challenge.dto;

import com.greeny.ecomate.challenge.entity.MyChallenge;

public record CreateMyChallengeRequestDto(
        String nickname,
        Long challengeId
) {

    public static CreateMyChallengeRequestDto of(String nickname, Long challengeId) {
        return new CreateMyChallengeRequestDto(nickname, challengeId);
    }

    public static CreateMyChallengeRequestDto from(MyChallenge entity) {
        return new CreateMyChallengeRequestDto(
                entity.getUser().getNickname(),
                entity.getChallenge().getChallengeId());
    }

}
