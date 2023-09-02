package com.greeny.ecomate.map.dto;

import com.greeny.ecomate.map.entity.EcoStore;
import com.greeny.ecomate.utils.imageUtil.ImageUtil;
import lombok.Data;

@Data
public class EcoStoreDto {

    private String storeName;
    private String description;
    private String image;
    private Double latitude;
    private Double longitude;
    private String address;
    private Long likeCnt;
    private Boolean liked;

    public EcoStoreDto(EcoStore ecoStore, Boolean liked) {
        this.storeName = ecoStore.getStoreName();
        this.description = ecoStore.getDescription();
        this.image = ImageUtil.getStoreImage(ecoStore.getImage());
        this.latitude = ecoStore.getLatitude();
        this.longitude = ecoStore.getLongitude();
        this.address = ecoStore.getAddress();
        this.likeCnt = ecoStore.getLikeCnt();
        this.liked = liked;
    }

}
