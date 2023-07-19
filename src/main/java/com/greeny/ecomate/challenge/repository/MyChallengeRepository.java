package com.greeny.ecomate.challenge.repository;

import com.greeny.ecomate.challenge.entity.MyChallenge;
import com.greeny.ecomate.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MyChallengeRepository extends JpaRepository<MyChallenge, Long> {

    List<MyChallenge> findAllByUser_UserId(Long userId);

    Optional<MyChallenge> findMyChallengeByUser_UserIdAndChallenge_ChallengeId(Long UserId, Long challengeId);

    Long countMyChallengesByChallenge_ChallengeId(Long challengeId);

}
