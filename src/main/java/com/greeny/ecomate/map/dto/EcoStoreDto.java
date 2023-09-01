package com.greeny.ecomate.map.dto;

import lombok.Data;

@Data
public class EcoStoreDto {

    private String storeName;
    private String description;
    private Double latitude;
    private Double longitude;
    private String address;
    private Long likeCnt;

    public EcoStoreDto(String storeName, String description, Double latitude, Double longitude, String address, Long likeCnt) {
        this.storeName = storeName;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.likeCnt = likeCnt;
    }

}
