package com.greeny.ecomate.challenge.dto;

import com.greeny.ecomate.challenge.entity.AchieveType;
import com.greeny.ecomate.challenge.entity.MyChallenge;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MyChallengeDto {

    private Long myChallengeId;
    private Long memberId;
    private String nickname;
    private Long challengeId;
    private String challengeTitle;
    private String description;
    private String image;
    private Long goalCnt;
    private Long treePoint;
    private AchieveType achieveType;
    private Long achieveCnt;
    private Long achievePoint;
    private Long doneCnt;
    private LocalDateTime createdDate;

    public MyChallengeDto(MyChallenge myChallenge) {
        this.myChallengeId = myChallenge.getMyChallengeId();
        this.memberId = myChallenge.getMember().getMemberId();
        this.nickname = myChallenge.getMember().getNickname();
        this.challengeId = myChallenge.getChallenge().getChallengeId();
        this.challengeTitle = myChallenge.getChallenge().getChallengeTitle();
        this.description = myChallenge.getChallenge().getDescription();
        this.image = myChallenge.getChallenge().getImage();
        this.goalCnt = myChallenge.getChallenge().getGoalCnt();
        this.treePoint = myChallenge.getChallenge().getTreePoint();
        this.achieveType = myChallenge.getAchieveType();
        this.achieveCnt = myChallenge.getAchieveCnt();
        this.achievePoint = myChallenge.getAchievePoint();
        this.doneCnt = myChallenge.getDoneCnt();
        this.createdDate = myChallenge.getCreatedDate();
    }

}
