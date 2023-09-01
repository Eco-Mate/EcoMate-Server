package com.greeny.ecomate.member.dto;

import com.greeny.ecomate.member.entity.Level;
import com.greeny.ecomate.member.entity.Role;
import com.greeny.ecomate.member.entity.Member;

public record MemberDto(
        Long memberId,
        Role role,
        String level,
        Long totalTreePoint,
        String profileImage,
        String nickname,
        String name,
        String email,
        String statusMessage,
        Long followerCnt,
        Long followingCnt
) {
    private MemberDto(Member member, String profileImage) {
         this(member.getMemberId(), member.getRole(), member.getLevel(), member.getTotalTreePoint(), profileImage,
                member.getNickname(), member.getName(), member.getEmail(), member.getStatusMessage(), member.getFollowerCnt(), member.getFollowingCnt());
    }

    private MemberDto(Member member) {
        this(member.getMemberId(), member.getRole(), member.getLevel(), member.getTotalTreePoint(), null,
                member.getNickname(), member.getName(), member.getEmail(), member.getStatusMessage(), member.getFollowerCnt(), member.getFollowingCnt());
    }

    public static MemberDto from(String profileImageUrl, Member member) {
        if (member.getProfileImage() != null) {
            return new MemberDto(member, profileImageUrl + "/" + member.getProfileImage());
        }
        return new MemberDto(member);
    }

}
