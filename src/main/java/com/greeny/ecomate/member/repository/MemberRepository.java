package com.greeny.ecomate.member.repository;

import com.greeny.ecomate.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByNickname(String nickname);

    Optional<Member> findByName(String name);

    Boolean existsMemberByEmail(String email);

    Boolean existsMemberByName(String name);

    Boolean existsMemberByNickname(String nickname);

}
