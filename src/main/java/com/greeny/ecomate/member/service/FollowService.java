package com.greeny.ecomate.member.service;

import com.greeny.ecomate.member.dto.FollowMemberDto;
import com.greeny.ecomate.member.entity.Follow;
import com.greeny.ecomate.member.entity.Member;
import com.greeny.ecomate.member.repository.FollowRepository;
import com.greeny.ecomate.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @Transactional
    public Long follow(Long fromMemberId, Long toMemberId) {
        if(checkFollowState(fromMemberId, toMemberId)) {
            throw new IllegalArgumentException("해당 멤버를 이미 팔로우 하였습니다.");
        }
        else {
            Follow follow = Follow.builder()
                    .fromMemberId(fromMemberId)
                    .toMemberId(toMemberId)
                    .build();
            updateFollowingCnt(fromMemberId, 1L);
            updateFollowerCnt(toMemberId, 1L);
            return followRepository.save(follow).getFollowId();
        }
    }

    @Transactional
    public void unfollow(Long fromMemberId, Long toMemberId) {
        if(checkFollowState(fromMemberId, toMemberId)) {
            Follow follow = followRepository.findFollowByFromMemberIdAndToMemberId(fromMemberId, toMemberId);
            followRepository.delete(follow);
            updateFollowingCnt(fromMemberId, -1L);
            updateFollowerCnt(toMemberId, -1L);
        }
        else {
            throw new IllegalStateException("해당 멤버를 팔로우 하지 않았습니다.");
        }
    }

    public boolean checkFollowState(Long fromMemberId, Long toMemberId) {
        return followRepository.existsFollowByFromMemberIdAndToMemberId(fromMemberId, toMemberId);
    }

    @Transactional
    public void updateFollowingCnt(Long fromMemberId, Long cnt) {
        Member fromMember = memberRepository.findByMemberId(fromMemberId).get();
        fromMember.updateFollowingCnt(fromMember.getFollowingCnt()+cnt);
    }

    @Transactional
    public void updateFollowerCnt(Long toMemberId, Long cnt) {
        Member toMember = memberRepository.findByMemberId(toMemberId).get();
        toMember.updateFollowerCnt(toMember.getFollowerCnt()+cnt);
    }

    public List<FollowMemberDto> getFollowings(Long memberId) {
        List<Long> followings = followRepository.findFollowsByFromMemberId(memberId).stream().map(Follow::getToMemberId).toList();
        return followings.stream().map(this::createFollowMemberDto).toList();
    }

    public List<FollowMemberDto> getFollowers(Long memberId) {
        List<Long> followers = followRepository.findFollowsByToMemberId(memberId).stream().map(Follow::getFromMemberId).toList();
        return followers.stream().map(this::createFollowMemberDto).toList();
    }

    private FollowMemberDto createFollowMemberDto(Long memberId) {
        Member member = memberService.getMemberById(memberId);
        return new FollowMemberDto(member);
    }

}
