package com.greeny.ecomate.member.dto;

import com.greeny.ecomate.member.entity.Level;
import com.greeny.ecomate.member.entity.Role;
import com.greeny.ecomate.member.entity.Member;

public record MemberDto(
        Long memberId,
        Role role,
        Level level,
        Long totalTreePoint,
        String nickname,
        String name,
        String email,
        String statusMessage
) {

    public static MemberDto from(Member entity) {
        return new MemberDto(
            entity.getMemberId(),
            entity.getRole(),
            entity.getLevel(),
            entity.getTotalTreePoint(),
            entity.getNickname(),
            entity.getName(),
            entity.getEmail(),
            entity.getStatusMessage()
        );
    }

}
