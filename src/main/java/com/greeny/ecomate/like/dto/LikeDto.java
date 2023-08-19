package com.greeny.ecomate.like.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LikeDto {

    private Long likeCnt;
    private Boolean liked;
}
