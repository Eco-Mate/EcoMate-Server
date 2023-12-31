package com.greeny.ecomate.challenge.entity;

import com.greeny.ecomate.base.BaseEntity;
import com.greeny.ecomate.challenge.dto.MyChallengeDto;
import com.greeny.ecomate.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyChallenge extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long myChallengeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @Enumerated(EnumType.STRING)
    private AchieveType achieveType;

    @Column(name = "achieve_cnt")
    private Long achieveCnt;

    @Column(name = "achieve_point")
    private Long achievePoint;

    @Column(name = "done_cnt")
    private Long doneCnt;

    @Builder
    public MyChallenge(Member member, Challenge challenge, AchieveType achieveType, Long achieveCnt, Long achievePoint, Long doneCnt) {
        this.member = member;
        this.challenge = challenge;
        this.achieveType = achieveType;
        this.achieveCnt = achieveCnt;
        this.achievePoint = achievePoint;
        this.doneCnt = doneCnt;
    }

    public void updateAchieveType(AchieveType achieveType) { this.achieveType = achieveType; }

    public void updateAchieveCnt(Long achieveCnt) { this.achieveCnt = achieveCnt; }
    public void updateAchievePoint(Long achievePoint) {
        this.achievePoint = achievePoint;
    }

    public void updateDoneCnt(Long doneCnt) { this.doneCnt = doneCnt; }

}
