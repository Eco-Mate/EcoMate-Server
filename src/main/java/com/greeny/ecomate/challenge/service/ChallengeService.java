package com.greeny.ecomate.challenge.service;


import com.greeny.ecomate.challenge.dto.ChallengeDto;
import com.greeny.ecomate.challenge.dto.CreateChallengeRequestDto;
import com.greeny.ecomate.challenge.dto.MyChallengeDto;
import com.greeny.ecomate.challenge.dto.UpdateChallengeRequestDto;
import com.greeny.ecomate.challenge.entity.AchieveType;
import com.greeny.ecomate.challenge.entity.Challenge;
import com.greeny.ecomate.challenge.repository.ChallengeRepository;
import com.greeny.ecomate.challenge.repository.MyChallengeRepository;
import com.greeny.ecomate.exception.NotFoundException;
import com.greeny.ecomate.exception.UnauthorizedAccessException;
import com.greeny.ecomate.member.entity.Member;
import com.greeny.ecomate.member.entity.Role;
import com.greeny.ecomate.member.repository.MemberRepository;
import com.greeny.ecomate.s3.service.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChallengeService {

    @Value("${s3-directory.challenge}")
    String challengeDirectory;

    @Value("${cloud.aws.s3.url}")
    String s3Url;

    private final ChallengeRepository challengeRepository;
    private final MyChallengeRepository myChallengeRepository;
    private final MemberRepository memberRepository;

    private final AwsS3Service awsS3Service;
    private final MyChallengeService myChallengeService;

    @Transactional
    public Long createChallenge(CreateChallengeRequestDto dto, Long memberId, MultipartFile file) {
        validateAuth(memberId);
        String fileName = uploadImage(file);
        Challenge challenge = Challenge.builder()
                .activeYn(dto.getActiveYn())
                .challengeTitle(dto.getChallengeTitle())
                .description(dto.getDescription())
                .image(fileName)
                .goalCnt(dto.getGoalCnt())
                .treePoint(dto.getTreePoint())
                .build();

        return challengeRepository.save(challenge).getChallengeId();
    }

    public ChallengeDto getChallengeById(Long challengeId) {
        Challenge findChallenge = findChallengeById(challengeId);
        return new ChallengeDto(findChallenge);
    }

    public List<ChallengeDto> findAllChallenge(Member member) {
        List<Challenge> challengeList;
        if(member.getRole() != Role.ROLE_ADMIN)
            challengeList = challengeRepository.findChallengesByActiveYn(true);
        else
            challengeList = challengeRepository.findAll();
        return challengeList.stream().map(this::createChallengeDto).toList();
    }

    public List<ChallengeDto> findBeforeStartChallenge(Member member) {
        List<Long> afterStartChallengeList = myChallengeService.getAllMyChallengeByMemberId(member.getMemberId()).stream().map(MyChallengeDto::getChallengeId).toList();
        List<Challenge> beforeStartChallengeList;
        if(member.getRole() != Role.ROLE_ADMIN)
            beforeStartChallengeList = challengeRepository.findChallengesByActiveYn(true);
        else
            beforeStartChallengeList = challengeRepository.findAll();

        for (Long idx : afterStartChallengeList) {
            Challenge challenge = findChallengeById(idx);
            beforeStartChallengeList.remove(challenge);
        }
        return beforeStartChallengeList.stream().map(this::createChallengeDto).toList();
    }

    public Long getProceedingChallengeCnt(Long challengeId) {
        return myChallengeRepository.countMyChallengesByChallenge_ChallengeIdAndAchieveType(challengeId, AchieveType.PROCEEDING);
    }

    @Transactional
    public void updateChallengeActiveYn(Long challengeId, boolean activeYn, Long memberId) {
        validateAuth(memberId);
        Challenge challenge = findChallengeById(challengeId);
        challenge.updateActiveYn(activeYn);
    }

    @Transactional
    public Long updateChallenge(Long challengeId, UpdateChallengeRequestDto dto, Long memberId) {
        validateAuth(memberId);
        Challenge challenge = findChallengeById(challengeId);
        challenge.updateTitle(dto.getChallengeTitle());
        challenge.updateDescription(dto.getDescription());
        challenge.updateGoalCnt(dto.getGoalCnt());
        challenge.updateTreePoint(dto.getTreePoint());
        return challenge.getChallengeId();
    }

    @Transactional
    public void deleteChallenge(Long challengeId, Long memberId) {
        validateAuth(memberId);
        Challenge challenge = findChallengeById(challengeId);
        challengeRepository.delete(challenge);
    }

    private String uploadImage(MultipartFile file){
        return awsS3Service.upload(challengeDirectory, file);
    }

    private ChallengeDto createChallengeDto(Challenge challenge) {
        return new ChallengeDto(challenge);
    }

    private void validateAuth(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다."));

        if(member.getRole() != Role.ROLE_ADMIN)
            throw new UnauthorizedAccessException("권한이 없습니다.");
    }

    private Challenge findChallengeById(Long challengeId) {
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 챌린지입니다."));
        return challenge;
    }

}
