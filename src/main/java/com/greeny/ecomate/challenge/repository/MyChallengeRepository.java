package com.greeny.ecomate.challenge.repository;

import com.greeny.ecomate.challenge.entity.AchieveType;
import com.greeny.ecomate.challenge.entity.MyChallenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MyChallengeRepository extends JpaRepository<MyChallenge, Long> {

    Optional<List<MyChallenge>> findAllByMember_MemberId(Long memberId);

    Optional<MyChallenge> findMyChallengeByMember_MemberIdAndChallenge_ChallengeId(Long memberId, Long challengeId);

    Optional<List<MyChallenge>> findAllByMember_MemberIdAndAchieveType(Long memberId, AchieveType achieveType);

    Optional<Long> countMyChallengesByMember_MemberIdAndAchieveType(Long memberId, AchieveType achieveType);

    @Query("SELECT mc from MyChallenge mc join fetch mc.member where mc.myChallengeId = :myChallengeId")
    Optional<MyChallenge> findMyChallengeByMyChallengeId(@Param(value = "myChallengeId") Long myChallengeId);

    Long countMyChallengesByChallenge_ChallengeIdAndAchieveType(Long challengeId, AchieveType achieveType);

    @Query("SELECT mc FROM MyChallenge mc join fetch mc.member where mc.challenge.challengeId = :challengeId and mc.member.memberId = :memberId and mc.achieveType = :achieveType")
    Optional<MyChallenge> findByChallengeIdAndMemberIdAndAchieveType(Long challengeId, Long memberId, AchieveType achieveType);
}
