package com.greeny.ecomate.map.entity;

import com.greeny.ecomate.base.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreLike extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "store_like_id")
    private Long storeLikeId;

    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private EcoStore ecoStore;

    public StoreLike(Long memberId, EcoStore ecoStore) {
        this.memberId = memberId;
        this.ecoStore = ecoStore;
    }

}
