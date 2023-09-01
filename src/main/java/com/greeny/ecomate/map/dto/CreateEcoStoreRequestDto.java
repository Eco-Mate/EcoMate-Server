package com.greeny.ecomate.map.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEcoStoreRequestDto {

    @NotNull
    private String storeName;
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
    @NotNull
    private String address;

}
