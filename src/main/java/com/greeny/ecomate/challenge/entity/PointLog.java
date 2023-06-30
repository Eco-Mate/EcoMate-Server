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
public class PointLog extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "point_id")
    private Long pointId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "challenge_id")
    private Long challengeId;

    @Column(name = "tree_point")
    private Long treePoint;
}
