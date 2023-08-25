package com.greeny.ecomate.member.entity;

import com.greeny.ecomate.base.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long followId;

    @Column(name = "from_member_id")
    private Long fromMemberId;

    @Column(name = "to_member_id")
    private Long toMemberId;

    @Builder
    public Follow(Long fromMemberId, Long toMemberId) {
        this.fromMemberId = fromMemberId;
        this.toMemberId = toMemberId;
    }

}
