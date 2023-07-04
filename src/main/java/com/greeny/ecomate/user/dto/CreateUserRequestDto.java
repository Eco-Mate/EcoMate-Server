package com.greeny.ecomate.user.dto;

import com.greeny.ecomate.user.entity.Level;
import com.greeny.ecomate.user.entity.Role;
import com.greeny.ecomate.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class CreateUserRequestDto {
    private String email;
    private String name;
    private String nickname;
    private String password;
    private String role;

    public User toEntity() {
        return User.builder()
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
