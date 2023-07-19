package com.greeny.ecomate.challenge.repository;

import com.greeny.ecomate.challenge.dto.MyChallengeDto;
import com.greeny.ecomate.challenge.entity.AchieveType;
import com.greeny.ecomate.challenge.entity.MyChallenge;
import com.greeny.ecomate.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MyChallengeRepository extends JpaRepository<MyChallenge, Long> {

    List<MyChallenge> findAllByUser_UserId(Long userId);

    Optional<MyChallenge> findMyChallengeByUser_UserIdAndChallenge_ChallengeId(Long UserId, Long challengeId);

    @Query("SELECT mc from MyChallenge mc join fetch mc.user where mc.myChallengeId = :myChallengeId")
    Optional<MyChallenge> findMyChallengeByMyChallengeId(@Param(value = "myChallengeId") Long myChallengeId);

    Long countMyChallengesByChallenge_ChallengeIdAndAchieveType(Long challengeId, AchieveType achieveType);

}
