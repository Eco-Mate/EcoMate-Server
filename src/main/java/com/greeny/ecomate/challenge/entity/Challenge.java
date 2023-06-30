package com.greeny.ecomate.challenge.entity;

import com.greeny.ecomate.base.BaseEntity;
import com.greeny.ecomate.user.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Challenge extends BaseEntity {

    @Id @GeneratedValue
    private Long challengeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "active_yn")
    private Boolean activeYn;

    @Column(name = "challenge_title", length = 30)
    private String challengeTitle;

    @Column(name = "description", length = 100)
    private String description;

    @Column(name = "tree_point")
    private Long treePoint;

    @Enumerated(EnumType.STRING)
    private AchieveType achieveType;
}
