package com.greeny.ecomate.auth.service;

import com.greeny.ecomate.auth.dto.SignUpForm;
import com.greeny.ecomate.exception.NotFoundException;
import com.greeny.ecomate.member.entity.Member;
import com.greeny.ecomate.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class authService {

    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    private boolean isDuplicateMemberName(String name) {
        return memberRepository.existsMemberByName(name);
    }

    private boolean isDuplicateMemberNickname(String nickname) {
        return memberRepository.existsMemberByNickname(nickname);
    }

    private boolean isDuplicateMemberEmail(String email) {
        return memberRepository.existsMemberByEmail(email);
    }

    @Transactional
    public Long signUp(SignUpForm form) throws RuntimeException {
        if(isDuplicateMemberName(form.getName())) {
            throw new IllegalStateException("이미 존재하는 아이디 입니다.");
        }
        if(isDuplicateMemberNickname(form.getNickname())) {
            throw new IllegalStateException("이미 존재하는 닉네임 입니다.");
        }
        if(isDuplicateMemberEmail(form.getEmail())) {
            throw new IllegalStateException("이미 존재하는 이메일 입니다.");
        }

        Member member = Member.builder()
                .name(form.getName())
                .password(passwordEncoder.encode(form.getPassword()))
                .nickname(form.getNickname())
                .email(form.getEmail())
                .build();

        memberRepository.save(member);

        return member.getMemberId();
    }

    public Member signIn(SignUpForm form) throws NotFoundException {
        Member findMember = memberRepository.findByName(form.getName())
                .orElseThrow(() -> new NotFoundException("계정이 존재하지 않습니다."));

        if(!passwordEncoder.matches(form.getPassword(), findMember.getPassword())) {
            throw new NotFoundException("비밀번호가 잘못되었습니다.");
        }

        return findMember;
    }

}
