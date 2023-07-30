package com.greeny.ecomate.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ChallengeListDto {

    private List<ChallengeDto> challengeDtoList;

}
