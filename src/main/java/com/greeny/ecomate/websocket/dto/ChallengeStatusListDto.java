package com.greeny.ecomate.websocket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ChallengeStatusListDto {
    List<ChallengeStatusDto> challengeStatusList;
}
