package com.greeny.ecomate.challenge.service;

import com.greeny.ecomate.challenge.dto.CreateMyChallengeRequestDto;
import com.greeny.ecomate.challenge.dto.MyChallengeDto;
import com.greeny.ecomate.challenge.entity.AchieveType;
import com.greeny.ecomate.challenge.entity.Challenge;
import com.greeny.ecomate.challenge.entity.MyChallenge;
import com.greeny.ecomate.challenge.repository.ChallengeRepository;
import com.greeny.ecomate.challenge.repository.MyChallengeRepository;
import com.greeny.ecomate.user.entity.User;
import com.greeny.ecomate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyChallengeService {

    private final MyChallengeRepository myChallengeRepository;
    private final UserRepository userRepository;

    private final ChallengeRepository challengeRepository;

    @Transactional
    public Long createMyChallenge(CreateMyChallengeRequestDto dto) {
        User user = userRepository.findByNickname(dto.nickname())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Challenge challenge = challengeRepository.findById(dto.challengeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 챌린지입니다."));

        // 새도전
        if(!myChallengeRepository.findMyChallengeByUser_UserIdAndChallenge_ChallengeId(user.getUserId(), challenge.getChallengeId()).isPresent()) {
            MyChallenge myChallenge = MyChallenge.builder()
                    .challenge(challenge)
                    .user(user)
                    .achieveType(AchieveType.PROCEEDING)
                    .achieveCnt(1L)
                    .achievePoint(challenge.getTreePoint())
                    .doneCnt(0L)
                    .build();
            return myChallengeRepository.save(myChallenge).getMyChallengeId();
        }
        // 재도전
        else {
             MyChallenge myChallenge = myChallengeRepository.findMyChallengeByUser_UserIdAndChallenge_ChallengeId(user.getUserId(), challenge.getChallengeId()).get();
             if(myChallenge.getAchieveType().equals(AchieveType.PROCEEDING)) {
                 throw new IllegalArgumentException("해당 도전은 진행 중입니다. 계속 도전해보세요!");
             }
             else {
                 myChallenge.updateAchieveCnt(myChallenge.getAchieveCnt() + 1);
                 myChallenge.updateAchievePoint(myChallenge.getAchievePoint() + challenge.getTreePoint());
                 myChallenge.updateDoneCnt(0L);
                 return myChallenge.getMyChallengeId();
             }
        }
    }

    public List<MyChallengeDto> getMyChallengeByUserId(Long userId) {
        List<MyChallenge> myChallengeList = myChallengeRepository.findAllByUser_UserId(userId);
        return myChallengeList.stream().map(MyChallengeDto::from).toList();
    }

    @Transactional
    public String updateMyChallengeDoneCnt(Long myChallengeId) {
        MyChallenge myChallenge = myChallengeRepository.findById(myChallengeId)
                .orElseThrow(() -> new IllegalArgumentException("도전하고 있지 않는 챌린지입니다."));

        if(myChallenge.getAchieveType() == AchieveType.FINISH) {
            return "이미 달성한 챌린지 입니다.";
        }

        if(myChallenge.getDoneCnt()+1 < myChallenge.getChallenge().getGoalCnt()) {
            myChallenge.updateDoneCnt(myChallenge.getDoneCnt() + 1);
            return "챌린지 도전 횟수가 1 증가했습니다.";
        }
        else {
            myChallenge.updateDoneCnt(myChallenge.getDoneCnt() + 1);
            myChallenge.updateAchieveType(AchieveType.FINISH);
            return "챌린지를 달성하였습니다. 축하드립니다!";
        }
    }

}
