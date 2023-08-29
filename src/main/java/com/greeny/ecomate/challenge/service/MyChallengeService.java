package com.greeny.ecomate.challenge.service;

import com.greeny.ecomate.challenge.dto.ChallengeDto;
import com.greeny.ecomate.challenge.dto.MyChallengeDto;
import com.greeny.ecomate.challenge.entity.AchieveType;
import com.greeny.ecomate.challenge.entity.Challenge;
import com.greeny.ecomate.challenge.entity.MyChallenge;
import com.greeny.ecomate.challenge.repository.ChallengeRepository;
import com.greeny.ecomate.challenge.repository.MyChallengeRepository;
import com.greeny.ecomate.exception.NotFoundException;
import com.greeny.ecomate.member.entity.Member;
import com.greeny.ecomate.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyChallengeService {

    @Value("${s3-directory.challenge}")
    String challengeDirectory;

    @Value("${cloud.aws.s3.url}")
    String s3Url;

    private final MyChallengeRepository myChallengeRepository;
    private final MemberRepository memberRepository;

    private final ChallengeRepository challengeRepository;

    @Transactional
    public Long createMyChallenge(Long challengeId, Long memberId) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 챌린지입니다."));

        if(!challenge.getActiveYn())
            throw new IllegalArgumentException("활성화되지 않은 챌린지 입니다.");

        // 새도전
        Optional<MyChallenge> mc = myChallengeRepository.findMyChallengeByMember_MemberIdAndChallenge_ChallengeId(memberId, challengeId);
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

    private MyChallengeDto createMyChallengeDto(MyChallenge myChallenge) {
        return new MyChallengeDto(myChallenge, s3Url, challengeDirectory);
    }

    public List<MyChallengeDto> getAllMyChallengeByMemberId(Long memberId) {
        List<MyChallenge> myChallengeList = myChallengeRepository.findAllByMember_MemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        return myChallengeList.stream().map(this::createMyChallengeDto).toList();
    }

    public List<MyChallengeDto> getAllMyChallengeProceedingByMemberId(Long memberId) {
        List<MyChallenge> myChallengeProceedingList = myChallengeRepository.findAllByMember_MemberIdAndAchieveType(memberId, AchieveType.PROCEEDING)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        return myChallengeProceedingList.stream().map(this::createMyChallengeDto).toList();
    }

    public List<MyChallengeDto> getAllMyChallengeFinishByMemberId(Long memberId) {
        List<MyChallenge> myChallengeFinishList = myChallengeRepository.findAllByMember_MemberIdAndAchieveType(memberId, AchieveType.FINISH)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        return myChallengeFinishList.stream().map(this::createMyChallengeDto).toList();
    }

    public Long getMyChallengeProceedingCntByMemberId(Long memberId) {
        Long proceedingCnt = myChallengeRepository.countMyChallengesByMember_MemberIdAndAchieveType(memberId, AchieveType.PROCEEDING)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        return proceedingCnt;
    }

    public Long getMyChallengeFinishCntByMemberId(Long memberId) {
        Long finishCnt = myChallengeRepository.countMyChallengesByMember_MemberIdAndAchieveType(memberId, AchieveType.FINISH)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        return finishCnt;
    }

    public MyChallengeDto getMyChallengeById(Long myChallengeId) {
        MyChallenge findMyChallenge = myChallengeRepository.findMyChallengeByMyChallengeId(myChallengeId)
                .orElseThrow(() -> new IllegalArgumentException("도전하지 않은 챌린지입니다."));
        return createMyChallengeDto(findMyChallenge);
    }

    @Transactional
    public String updateMyChallengeDoneCnt(Long challengeId, Long memberId) {
        MyChallenge myChallenge = myChallengeRepository.findByChallengeIdAndMemberIdAndAchieveType(challengeId, memberId, AchieveType.PROCEEDING)
                .orElseThrow(() -> new IllegalArgumentException("도전하고 있지 않는 챌린지입니다."));

        if(!myChallenge.getMember().getMemberId().equals(memberId))
            throw new IllegalStateException("수정 권한이 없습니다.");

        if(myChallenge.getAchieveType() != AchieveType.PROCEEDING) {
            throw new IllegalStateException("진행 중인 챌린지가 아닙니다.");
        }

        if(myChallenge.getDoneCnt()+1 < myChallenge.getChallenge().getGoalCnt()) {
            myChallenge.updateDoneCnt(myChallenge.getDoneCnt() + 1);
            return "챌린지 도전 횟수가 1 증가했습니다.";
        }
        else {
            myChallenge.updateDoneCnt(myChallenge.getDoneCnt() + 1);
            myChallenge.updateAchieveType(AchieveType.FINISH);

            Long userId = myChallenge.getMember().getMemberId();
            Member member = memberRepository.findById(userId)
                    .orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다."));
            member.updateTotalTreePoint(member.getTotalTreePoint() + myChallenge.getAchievePoint());

            return "챌린지를 달성 완료하여 트리포인트가 적립되었습니다. 축하드립니다!";
        }
    }

    @Transactional
    public void deleteMyChallenge(Long myChallengeId, Long memberId) {
        MyChallenge myChallenge = myChallengeRepository.findMyChallengeByMyChallengeId(myChallengeId)
                .orElseThrow(() -> new IllegalArgumentException("도전하지 않은 챌린지입니다."));

        if(!myChallenge.getMember().getMemberId().equals(memberId))
            throw new IllegalStateException("삭제 권한이 없습니다.");

        myChallengeRepository.delete(myChallenge);
    }

}
