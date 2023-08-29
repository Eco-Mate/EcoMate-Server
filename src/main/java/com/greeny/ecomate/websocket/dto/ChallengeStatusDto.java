package com.greeny.ecomate.websocket.dto;

import com.greeny.ecomate.member.entity.Member;
import com.greeny.ecomate.utils.imageUtil.ImageUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChallengeStatusDto {
    private String nickname;
    private String profileImage;
    private Long challengeDoneCnt;

    public ChallengeStatusDto(Member member, Long challengeDoneCnt) {
        this.nickname = member.getNickname();
        this.profileImage = ImageUtil.getProfileImage(member.getProfileImage());
        this.challengeDoneCnt = challengeDoneCnt;
    }

}
