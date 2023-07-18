package com.greeny.ecomate.challenge.repository;

import com.greeny.ecomate.challenge.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

}
