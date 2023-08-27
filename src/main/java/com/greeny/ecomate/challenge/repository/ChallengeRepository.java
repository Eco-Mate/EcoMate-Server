package com.greeny.ecomate.challenge.repository;

import com.greeny.ecomate.challenge.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    List<Challenge> findChallengesByActiveYn(Boolean activeYn);

}
