package com.greeny.ecomate.challenge.repository;

import com.greeny.ecomate.challenge.entity.AchieveType;
import com.greeny.ecomate.challenge.entity.MyChallenge;
import com.greeny.ecomate.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MyChallengeRepository extends JpaRepository<MyChallenge, Long> {

    Optional<List<MyChallenge>> findAllByMember_MemberId(Long memberId);

    Optional<MyChallenge> findMyChallengeByMember_MemberIdAndChallenge_ChallengeId(Long memberId, Long challengeId);

    Boolean existsMyChallengeByMember_MemberIdAndChallenge_ChallengeId(Long memberId, Long challengeId);

    Optional<List<MyChallenge>> findAllByMember_MemberIdAndAchieveType(Long memberId, AchieveType achieveType);

    Long countMyChallengesByChallenge_ChallengeIdAndAchieveType(Long challengeId, AchieveType achieveType);

    List<MyChallenge> findAllByMember(Member member);

}
