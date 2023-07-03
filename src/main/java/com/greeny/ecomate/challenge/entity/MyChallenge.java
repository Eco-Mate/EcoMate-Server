package com.greeny.ecomate.challenge.entity;

import com.greeny.ecomate.user.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyChallenge {

    @Id
    @GeneratedValue
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

}
