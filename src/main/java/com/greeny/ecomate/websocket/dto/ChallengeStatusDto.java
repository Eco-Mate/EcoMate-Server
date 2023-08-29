package com.greeny.ecomate.websocket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChallengeStatusDto {
    private String nickname;
    private String profileImage;
    private Long challengeDoneCnt;

}
