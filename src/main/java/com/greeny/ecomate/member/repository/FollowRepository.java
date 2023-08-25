package com.greeny.ecomate.member.repository;

import com.greeny.ecomate.member.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Boolean existsFollowByFromMemberIdAndToMemberId(Long fromMemberId, Long toMemberId);

    Follow findFollowByFromMemberIdAndToMemberId(Long fromMemberId, Long toMemberId);

    List<Follow> findFollowsByFromMemberId(Long fromMemberId);

}
