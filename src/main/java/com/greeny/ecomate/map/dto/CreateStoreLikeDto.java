package com.greeny.ecomate.map.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateStoreLikeDto {

    @NotNull
    private Long storeId;

}
