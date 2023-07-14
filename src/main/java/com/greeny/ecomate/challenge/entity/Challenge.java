package com.greeny.ecomate.challenge.entity;

import com.greeny.ecomate.base.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Challenge extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long challengeId;

    @Column(name = "active_yn")
    private Boolean activeYn;

    @Column(name = "challenge_title", unique = true, length = 30)
    private String challengeTitle;

    @Column(name = "description", length = 100)
    private String description;

    @Column(name = "tree_point")
    private Long treePoint;

    @Builder
    public Challenge(Boolean activeYn, String challengeTitle, String description, Long treePoint) {
        this.activeYn = activeYn;
        this.challengeTitle = challengeTitle;
        this.description = description;
        this.treePoint = treePoint;
    }

    public static Challenge of(Boolean activeYn, String challengeTitle, String description, Long treePoint) {
        return new Challenge(activeYn, challengeTitle, description, treePoint);
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

    public void updateTreePoint(Long treePoint) {
        this.treePoint = treePoint;
    }

}
