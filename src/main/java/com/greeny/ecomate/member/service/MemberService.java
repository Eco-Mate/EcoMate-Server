package com.greeny.ecomate.member.service;

import com.greeny.ecomate.exception.NotFoundException;
import com.greeny.ecomate.member.dto.CreateMemberRequestDto;
import com.greeny.ecomate.member.dto.MemberDto;
import com.greeny.ecomate.member.entity.Member;
import com.greeny.ecomate.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Long createMember(CreateMemberRequestDto createDto) {
        return memberRepository.save(createDto.toEntity()).getMemberId();
    }

    public List<MemberDto> getAllMember() {
        List<Member> memberList = memberRepository.findAll();
        return memberList.stream().map(MemberDto::from).toList();
    }

    public Member getMemberById(Long memberId) {
        return memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다."));
    }

}
