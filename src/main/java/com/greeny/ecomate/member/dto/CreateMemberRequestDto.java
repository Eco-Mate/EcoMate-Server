package com.greeny.ecomate.member.dto;

import com.greeny.ecomate.member.entity.Level;
import com.greeny.ecomate.member.entity.Role;
import com.greeny.ecomate.member.entity.Member;
import lombok.Data;

@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class CreateMemberRequestDto {
    private String email;
    private String name;
    private String nickname;
    private String password;
    private String role;

    public Member toEntity() {
        return Member.builder()
                .role(Role.valueOf(role))
                .level(Level.TREE)
                .totalTreePoint(0L)
                .nickname(nickname)
                .name(name)
                .password(password)
                .email(email)
                .build();
    }
}
