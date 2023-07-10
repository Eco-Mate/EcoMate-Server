package com.greeny.ecomate.challenge.service;


import com.greeny.ecomate.challenge.dto.ChallengeDto;
import com.greeny.ecomate.challenge.dto.CreateChallengeRequestDto;
import com.greeny.ecomate.challenge.entity.Challenge;
import com.greeny.ecomate.challenge.repository.ChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;

    @Transactional
    public Long createChallenge(CreateChallengeRequestDto dto) {
        Challenge challenge = dto.toEntity();
        return challengeRepository.save(challenge).getChallengeId();
    }

    public Challenge getChallengeById(Long challengeId) {
        Challenge findChallenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new IllegalArgumentException("챌린지를 찾을 수 없습니다."));
        return findChallenge;
    }

    public List<ChallengeDto> findAllChallenge() {
        List<Challenge> challengeList = challengeRepository.findAll();
        return challengeList.stream().map(ChallengeDto::from).toList();
    }



}
