package com.greeny.ecomate.map.dto;

import lombok.Data;

@Data
public class UpdateEcoStoreRequestDto {

    private String storeName;
    private Double latitude;
    private Double longitude;
    private String address;

}
