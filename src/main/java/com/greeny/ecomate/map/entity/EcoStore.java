package com.greeny.ecomate.map.entity;

import com.greeny.ecomate.base.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EcoStore extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "description")
    private String description;

    @Column(name = "latitude")
    private Double latitude; // 위도

    @Column(name = "longitude")
    private Double longitude; // 경도

    @Column(name = "address")
    private String address;

    @Column(name = "like_cnt")
    private Long likeCnt;

    @Builder
    public EcoStore(Long memberId, String storeName, String description, Double latitude, Double longitude, String address, Long likeCnt) {
        this.memberId = memberId;
        this.storeName = storeName;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.likeCnt = likeCnt;
    }

    public void update(String storeName, String description, Double latitude, Double longitude, String address) {
        this.storeName = storeName;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public void increaseLike() { this.likeCnt++; }

}
