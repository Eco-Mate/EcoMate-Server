package com.greeny.ecomate.challenge.service;

import com.greeny.ecomate.challenge.dto.MyChallengeDto;
import com.greeny.ecomate.challenge.entity.AchieveType;
import com.greeny.ecomate.challenge.entity.Challenge;
import com.greeny.ecomate.challenge.entity.MyChallenge;
import com.greeny.ecomate.challenge.repository.ChallengeRepository;
import com.greeny.ecomate.challenge.repository.MyChallengeRepository;
import com.greeny.ecomate.exception.NotFoundException;
import com.greeny.ecomate.exception.UnauthorizedAccessException;
import com.greeny.ecomate.member.entity.Member;
import com.greeny.ecomate.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        Member member = findMemberById(memberId);
        Challenge challenge = findChallengeById(challengeId);

        if(!challenge.getActiveYn())
            throw new AccessDeniedException("활성화되지 않은 챌린지 입니다.");

        // 새도전
        if(!myChallengeRepository.existsMyChallengeByMember_MemberIdAndChallenge_ChallengeId(memberId, challengeId)) {
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
             MyChallenge myChallenge = findMyChallengeByMemberIdAndChallengeId(memberId, challengeId);
             if(myChallenge.getAchieveType().equals(AchieveType.PROCEEDING)) {
                 throw new IllegalStateException("해당 도전은 진행 중입니다. 계속 도전해보세요!");
             }
             else if(myChallenge.getAchieveType().equals(AchieveType.FINISH)) {
                 myChallenge.updateAchieveType(AchieveType.PROCEEDING);
                 myChallenge.updateAchieveCnt(myChallenge.getAchieveCnt() + 1);
                 myChallenge.updateAchievePoint(myChallenge.getAchievePoint() + challenge.getTreePoint());
                 myChallenge.updateDoneCnt(0L);
                 return myChallenge.getMyChallengeId();
             }
             else {
                 throw new IllegalStateException("해당 도전을 시작하지 않았습니다.");
             }
        }
    }

    public List<MyChallengeDto> getAllMyChallengeByMemberId(Long memberId) {
        List<MyChallenge> myChallengeList = myChallengeRepository.findAllByMember_MemberId(memberId)
                .orElseThrow(() -> new NotFoundException("해당 사용자의 도전 챌린지가 없습니다."));
        return myChallengeList.stream().map(this::createMyChallengeDto).toList();
    }

    public List<MyChallengeDto> getAllMyChallengeProceedingByMemberId(Long memberId) {
        List<MyChallenge> myChallengeProceedingList = findAllMyChallengesByMemberIdAndAchieveType(memberId, AchieveType.PROCEEDING);
        return myChallengeProceedingList.stream().map(this::createMyChallengeDto).toList();
    }

    public List<MyChallengeDto> getAllMyChallengeFinishByMemberId(Long memberId) {
        List<MyChallenge> myChallengeFinishList = findAllMyChallengesByMemberIdAndAchieveType(memberId, AchieveType.FINISH);
        return myChallengeFinishList.stream().map(this::createMyChallengeDto).toList();
    }

    public Long getMyChallengeProceedingCntByMemberId(Long memberId) {
        List<MyChallenge> myChallengeProceedingList = findAllMyChallengesByMemberIdAndAchieveType(memberId, AchieveType.PROCEEDING);
        return myChallengeProceedingList.stream().count();
    }

    public Long getMyChallengeFinishCntByMemberId(Long memberId) {
        List<MyChallenge> myChallengeFinishList = findAllMyChallengesByMemberIdAndAchieveType(memberId, AchieveType.FINISH);
        return myChallengeFinishList.stream().count();
    }

    public MyChallengeDto getMyChallengeById(Long myChallengeId) {
        MyChallenge findMyChallenge = findMyChallengeById(myChallengeId);
        return createMyChallengeDto(findMyChallenge);
    }

    @Transactional
    public String updateMyChallengeDoneCnt(Long challengeId, Long memberId) {
        MyChallenge myChallenge = findMyChallengeByMemberIdAndChallengeId(memberId,challengeId);
        validateAuth(myChallenge, memberId);

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

            Member member = findMemberById(memberId);
            member.updateTotalTreePoint(member.getTotalTreePoint() + myChallenge.getAchievePoint());
            return "챌린지를 달성 완료하여 트리포인트가 적립되었습니다. 축하드립니다!";
        }
    }

    @Transactional
    public void deleteMyChallenge(Long myChallengeId, Long memberId) {
        MyChallenge myChallenge = findMyChallengeById(myChallengeId);
        validateAuth(myChallenge, memberId);
        myChallengeRepository.delete(myChallenge);
    }

    private MyChallengeDto createMyChallengeDto(MyChallenge myChallenge) {
        return new MyChallengeDto(myChallenge, s3Url, challengeDirectory);
    }

    private void validateAuth(MyChallenge myChallenge, Long memberId) {
        if(!myChallenge.getMember().getMemberId().equals(memberId))
            throw new UnauthorizedAccessException("권한이 없습니다.");
    }

    private MyChallenge findMyChallengeById(Long myChallengeId) {
        return myChallengeRepository.findById(myChallengeId)
                .orElseThrow(() -> new NotFoundException("도전하지 않은 챌린지입니다."));
    }

    private MyChallenge findMyChallengeByMemberIdAndChallengeId(Long memberId, Long challengeId) {
        return myChallengeRepository.findMyChallengeByMember_MemberIdAndChallenge_ChallengeId(memberId, challengeId)
                .orElseThrow(() -> new NotFoundException("도전하지 않은 챌린지입니다."));
    }

    private List<MyChallenge> findAllMyChallengesByMemberIdAndAchieveType(Long memberId, AchieveType achieveType) {
        return myChallengeRepository.findAllByMember_MemberIdAndAchieveType(memberId, achieveType)
                .orElseThrow(() -> new NotFoundException("조건 상태에 해당하는 도전 챌린지가 없습니다."));
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다."));
    }

    private Challenge findChallengeById(Long challengeId) {
        return challengeRepository.findById(challengeId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 챌린지입니다."));
    }

}
