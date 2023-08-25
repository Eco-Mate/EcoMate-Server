package com.greeny.ecomate.member.repository;

import com.greeny.ecomate.member.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Boolean existsFollowByFromMemberIdAndToMemberId(Long fromMemberId, Long toMemberId);

    Follow findFollowByFromMemberIdAndToMemberId(Long fromMemberId, Long toMemberId);

}
