package com.greeny.ecomate.challenge.service;


import com.greeny.ecomate.challenge.dto.ChallengeDto;
import com.greeny.ecomate.challenge.dto.CreateChallengeRequestDto;
import com.greeny.ecomate.challenge.dto.UpdateChallengeRequestDto;
import com.greeny.ecomate.challenge.entity.AchieveType;
import com.greeny.ecomate.challenge.entity.Challenge;
import com.greeny.ecomate.challenge.repository.ChallengeRepository;
import com.greeny.ecomate.challenge.repository.MyChallengeRepository;
import com.greeny.ecomate.member.entity.Member;
import com.greeny.ecomate.member.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final MyChallengeRepository myChallengeRepository;

    @Transactional
    public Long createChallenge(CreateChallengeRequestDto dto, Member member) {
        if(member.getRole() != Role.ROLE_ADMIN)
            throw new IllegalStateException("챌린지 생성 권한이 없습니다.");

        Challenge challenge = Challenge.builder()
                .activeYn(dto.getActiveYn())
                .challengeTitle(dto.getChallengeTitle())
                .description(dto.getDescription())
                .goalCnt(dto.getGoalCnt())
                .treePoint(dto.getTreePoint())
                .build();

        return challengeRepository.save(challenge).getChallengeId();
    }

    public Challenge getChallengeById(Long challengeId) {
        Challenge findChallenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 챌린지입니다."));
        return findChallenge;
    }

    private ChallengeDto createChallengeDto(Challenge challenge) {
        return new ChallengeDto(challenge);
    }

    public List<ChallengeDto> findAllChallenge() {
        List<Challenge> challengeList = challengeRepository.findAll();
        return challengeList.stream().map(this::createChallengeDto).toList();
    }

    public Long getProceedingChallengeCnt(Long challengeId) {
        return myChallengeRepository.countMyChallengesByChallenge_ChallengeIdAndAchieveType(challengeId, AchieveType.PROCEEDING);
    }

    @Transactional
    public void updateChallengeActiveYn(Long challengeId, boolean activeYn, Member member) {
        if(member.getRole() != Role.ROLE_ADMIN)
            throw new IllegalStateException("챌린지 활성화 수정 권한이 없습니다.");

        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 챌린지입니다."));

        challenge.updateActiveYn(activeYn);
    }

    @Transactional
    public Long updateChallenge(Long challengeId, UpdateChallengeRequestDto dto, Member member) {
        if(member.getRole() != Role.ROLE_ADMIN)
            throw new IllegalStateException("챌린지 내용 수정 권한이 없습니다.");

        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 챌린지입니다."));

        challenge.updateTitle(dto.getChallengeTitle());
        challenge.updateDescription(dto.getDescription());
        challenge.updateGoalCnt(dto.getGoalCnt());
        challenge.updateTreePoint(dto.getTreePoint());
        return challenge.getChallengeId();
    }

    @Transactional
    public void deleteChallenge(Long challengeId, Member member) {
        if(member.getRole() != Role.ROLE_ADMIN)
            throw new IllegalStateException("챌린지 삭제 권한이 없습니다.");

        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new IllegalArgumentException("챌린지가 존재하지 않습니다."));

        challengeRepository.delete(challenge);
    }

}
