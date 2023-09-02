package com.greeny.ecomate.map.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StoreLikeDto {

    private Long likeCnt;
    private Boolean liked;

}
