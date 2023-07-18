package com.greeny.ecomate.challenge.entity;

import com.greeny.ecomate.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyChallenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long myChallengeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @Enumerated(EnumType.STRING)
    private AchieveType achieveType;

    @Column(name = "achieve_cnt")
    private Long achieveCnt;

    @Column(name = "achieve_point")
    private Long achievePoint;

    @Builder
    public MyChallenge(User user, Challenge challenge,AchieveType achieveType, Long achieveCnt, Long achievePoint) {
        this.user = user;
        this.challenge = challenge;
        this.achieveType =achieveType;
        this.achieveCnt = achieveCnt;
        this.achievePoint = achievePoint;
    }

    public static MyChallenge of(User user, Challenge challenge, AchieveType achieveType, Long achieveCnt, Long achievePoint) {
        return new MyChallenge(user, challenge, achieveType, achieveCnt, achievePoint);
    }

    public void updateAchieveCnt(Long achieveCnt) { this.achieveCnt = achieveCnt; }
    public void updateAchievePoint(Long achievePoint) {
        this.achievePoint = achievePoint;
    }

}
