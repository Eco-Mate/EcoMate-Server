package com.greeny.ecomate.member.dto;

import com.greeny.ecomate.member.entity.Member;
import lombok.Data;

@Data
public class FollowMemberDto {

    private Long memberId;
    private String nickname;
    private String image;

    public FollowMemberDto(Member member) {
        this.memberId = member.getMemberId();
        this.nickname = member.getNickname();
        this.image = member.getProfileImage();
    }

}
