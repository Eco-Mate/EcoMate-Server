package com.greeny.ecomate.user.dto;

import com.greeny.ecomate.user.entity.Level;
import com.greeny.ecomate.user.entity.Role;
import com.greeny.ecomate.user.entity.User;
import lombok.Data;

public record UserDto(
        Long userId,
        Role role,
        Level level,
        Long totalTreePoint,
        String nickname,
        String name,
        String email
) {

    public static UserDto from(User entity) {
        return new UserDto(
            entity.getUserId(),
            entity.getRole(),
            entity.getLevel(),
            entity.getTotalTreePoint(),
            entity.getNickname(),
            entity.getName(),
            entity.getEmail()
        );
    }

}
