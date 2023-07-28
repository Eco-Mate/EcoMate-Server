package com.greeny.ecomate.challenge.service;

import com.greeny.ecomate.challenge.dto.CreateMyChallengeRequestDto;
import com.greeny.ecomate.challenge.dto.MyChallengeDto;
import com.greeny.ecomate.challenge.entity.AchieveType;
import com.greeny.ecomate.challenge.entity.Challenge;
import com.greeny.ecomate.challenge.entity.MyChallenge;
import com.greeny.ecomate.challenge.repository.ChallengeRepository;
import com.greeny.ecomate.challenge.repository.MyChallengeRepository;
import com.greeny.ecomate.member.entity.Member;
import com.greeny.ecomate.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyChallengeService {

    private final MyChallengeRepository myChallengeRepository;
    private final MemberRepository memberRepository;

    private final ChallengeRepository challengeRepository;

    @Transactional
    public Long createMyChallenge(CreateMyChallengeRequestDto dto) {
        Member member = memberRepository.findByNickname(dto.nickname())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Challenge challenge = challengeRepository.findById(dto.challengeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 챌린지입니다."));

        if(!challenge.getActiveYn())
            throw new IllegalArgumentException("활성화되지 않은 챌린지 입니다.");

        // 새도전
        Optional<MyChallenge> mc = myChallengeRepository.findMyChallengeByMember_MemberIdAndChallenge_ChallengeId(member.getMemberId(), challenge.getChallengeId());
        if(mc.isEmpty()) {
            MyChallenge myChallenge = MyChallenge.builder()
                    .challenge(challenge)
                    .member(member)
                    .achieveType(AchieveType.PROCEEDING)
                    .achieveCnt(1L)
                    .achievePoint(challenge.getTreePoint())
                    .doneCnt(0L)
                    .build();
            return myChallengeRepository.save(myChallenge).getMyChallengeId();
        }
        // 재도전
        else {
             MyChallenge myChallenge = mc.get();
             if(myChallenge.getAchieveType().equals(AchieveType.PROCEEDING)) {
                 throw new IllegalArgumentException("해당 도전은 진행 중입니다. 계속 도전해보세요!");
             }
             else if(myChallenge.getAchieveType().equals(AchieveType.FINISH)) {
                 myChallenge.updateAchieveType(AchieveType.PROCEEDING);
                 myChallenge.updateAchieveCnt(myChallenge.getAchieveCnt() + 1);
                 myChallenge.updateAchievePoint(myChallenge.getAchievePoint() + challenge.getTreePoint());
                 myChallenge.updateDoneCnt(0L);
                 return myChallenge.getMyChallengeId();
             }
             else {
                 throw new IllegalArgumentException("해당 도전을 시작하지 않았습니다.");
             }
        }
    }

    public List<MyChallengeDto> getAllMyChallengeByMemberId(Long memberId) {
        List<MyChallenge> myChallengeList = myChallengeRepository.findAllByMember_MemberId(memberId);
        return myChallengeList.stream().map(MyChallengeDto::from).toList();
    }

    public List<MyChallengeDto> getAllMyChallengeProceedingByMemberId(Long memberId) {
        List<MyChallenge> myChallengeProceedingList = myChallengeRepository.findAllByMember_MemberIdAndAchieveType(memberId, AchieveType.PROCEEDING);
        return myChallengeProceedingList.stream().map(MyChallengeDto::from).toList();
    }

    public List<MyChallengeDto> getAllMyChallengeFinishByMemberId(Long memberId) {
        List<MyChallenge> myChallengeFinishList = myChallengeRepository.findAllByMember_MemberIdAndAchieveType(memberId, AchieveType.FINISH);
        return myChallengeFinishList.stream().map(MyChallengeDto::from).toList();
    }

    public Long getMyChallengeProceedingCntByMemberId(Long memberId) {
        return myChallengeRepository.countMyChallengesByMember_MemberIdAndAchieveType(memberId, AchieveType.PROCEEDING);
    }

    public Long getMyChallengeFinishCntByMemberId(Long memberId) {
        return myChallengeRepository.countMyChallengesByMember_MemberIdAndAchieveType(memberId, AchieveType.FINISH);
    }

    public MyChallengeDto getMyChallengeById(Long myChallengeId) {
        MyChallenge findMyChallenge = myChallengeRepository.findMyChallengeByMyChallengeId(myChallengeId)
                .orElseThrow(() -> new IllegalArgumentException("도전하지 않은 챌린지입니다."));
        return findMyChallenge.from(findMyChallenge);
    }

    @Transactional
    public String updateMyChallengeDoneCnt(Long myChallengeId) {
        MyChallenge myChallenge = myChallengeRepository.findById(myChallengeId)
                .orElseThrow(() -> new IllegalArgumentException("도전하고 있지 않는 챌린지입니다."));

        //TODO: 로그인된 사용자와 해당 챌린지를 도전하고 있는 사용자가 일치하는지 확인

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

            Long userId = myChallenge.getMember().getMemberId();
            Member member = memberRepository.findById(userId).get();
            member.updateTotalTreePoint(member.getTotalTreePoint() + myChallenge.getAchievePoint());

            return "챌린지를 달성 완료하여 트리포인트가 적립되었습니다. 축하드립니다!";
        }
    }

    @Transactional
    public void deleteMyChallenge(Long myChallengeId) {
        MyChallenge myChallenge = myChallengeRepository.findMyChallengeByMyChallengeId(myChallengeId)
                .orElseThrow(() -> new IllegalArgumentException("도전하지 않은 챌린지입니다."));
        myChallengeRepository.delete(myChallenge);
    }

}
