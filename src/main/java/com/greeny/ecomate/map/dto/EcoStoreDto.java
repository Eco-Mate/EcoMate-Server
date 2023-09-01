package com.greeny.ecomate.map.dto;

import lombok.Data;

@Data
public class EcoStoreDto {

    private String storeName;
    private Double latitude;
    private Double longitude;
    private String address;
    private Long likeCnt;

    public EcoStoreDto(String storeName, Double latitude, Double longitude, String address, Long likeCnt) {
        this.storeName = storeName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.likeCnt = likeCnt;
    }

}
