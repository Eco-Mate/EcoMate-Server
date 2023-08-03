package com.greeny.ecomate.challenge.entity;

import com.greeny.ecomate.base.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Challenge extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "challenge_id")
    private Long challengeId;

    @Column(name = "active_yn")
    private Boolean activeYn;

    @Column(name = "challenge_title", unique = true, length = 30)
    private String challengeTitle;

    @Column(name = "description", length = 100)
    private String description;

    @Column(name = "image", length = 200)
    private String image;

    @Column(name = "goal_cnt")
    private Long goalCnt;

    @Column(name = "tree_point")
    private Long treePoint;

    @Builder
    public Challenge(Boolean activeYn, String challengeTitle, String description, String image, Long goalCnt, Long treePoint) {
        this.activeYn = activeYn;
        this.challengeTitle = challengeTitle;
        this.description = description;
        this.image = image;
        this.goalCnt = goalCnt;
        this.treePoint = treePoint;
    }

    public void updateActiveYn(boolean activeYn) {
        this.activeYn = activeYn;
    }

    public void updateTitle(String challengeTitle) {
        this.challengeTitle = challengeTitle;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public void updateGoalCnt(Long goalCnt) { this.goalCnt = goalCnt;}

    public void updateTreePoint(Long treePoint) {
        this.treePoint = treePoint;
    }

}
