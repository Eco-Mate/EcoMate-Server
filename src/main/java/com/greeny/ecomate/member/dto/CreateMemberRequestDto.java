package com.greeny.ecomate.member.dto;

import com.greeny.ecomate.member.entity.Level;
import com.greeny.ecomate.member.entity.Role;
import com.greeny.ecomate.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateMemberRequestDto {
    private String email;
    private String name;
    private String nickname;
    private String password;
    private String role;
    private String statusMessage;

    public Member toEntity() {
        return Member.builder()
                .role(Role.valueOf(role))
                .level("Seed1")
                .totalTreePoint(0L)
                .nickname(nickname)
                .name(name)
                .password(password)
                .email(email)
                .statusMessage(statusMessage)
                .build();
    }
}
