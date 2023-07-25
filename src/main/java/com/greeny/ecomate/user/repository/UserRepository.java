package com.greeny.ecomate.user.repository;

import com.greeny.ecomate.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNickname(String nickname);

    Optional<User> findByName(String name);

    Boolean existsUserByEmail(String email);

    Boolean existsUserByName(String name);

    Boolean exitsUserByNickname(String nickname);
}
