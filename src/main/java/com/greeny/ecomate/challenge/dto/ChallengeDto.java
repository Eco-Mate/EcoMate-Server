package com.greeny.ecomate.challenge.dto;

import com.greeny.ecomate.challenge.entity.Challenge;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChallengeDto {

    private Long challengeId;
    private Boolean activeYn;
    private String challengeTitle;
    private String description;
    private String image;
    private Long goalCnt;
    private Long treePoint;
    private LocalDateTime createdDate;

    public ChallengeDto(Challenge challenge, String s3Url, String challengeDirectory) {
        this.challengeId = challenge.getChallengeId();
        this.activeYn = challenge.getActiveYn();
        this.challengeTitle = challenge.getChallengeTitle();
        this.description = challenge.getDescription();
        this.image = s3Url + "/" + challengeDirectory + "/" + challenge.getImage();
        this.goalCnt = challenge.getGoalCnt();
        this.treePoint = challenge.getTreePoint();
        this.createdDate = challenge.getCreatedDate();
    }

}
