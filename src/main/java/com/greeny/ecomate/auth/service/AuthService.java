package com.greeny.ecomate.auth.service;

import com.greeny.ecomate.auth.dto.SignInForm;
import com.greeny.ecomate.auth.dto.SignUpForm;
import com.greeny.ecomate.exception.NotAuthenticatedException;
import com.greeny.ecomate.exception.NotFoundException;
import com.greeny.ecomate.member.entity.Member;
import com.greeny.ecomate.member.entity.Role;
import com.greeny.ecomate.member.repository.MemberRepository;
import com.greeny.ecomate.utils.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final RedisUtil redisUtil;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    private final String VERIFIED_PREFIX = "vf::";

    private void validateMemberNameDuplicated(String name) {
        if (memberRepository.existsMemberByName(name)) {
            throw new IllegalStateException("이미 존재하는 아이디 입니다.");
        }
    }

    private void validateMemberNicknameDuplicated(String nickname) {
        if (memberRepository.existsMemberByNickname(nickname)) {
            throw new IllegalStateException("이미 존재하는 닉네임 입니다.");
        }
    }

    private void validateMemberEmailDuplicated(String email) {
        if (memberRepository.existsMemberByEmail(email)) {
            throw new IllegalStateException("이미 존재하는 이메일 입니다.");
        }
    }

    @Transactional
    public Long signUpMember(SignUpForm form) throws RuntimeException {

        validateMemberNameDuplicated(form.getName());
        validateMemberNicknameDuplicated(form.getNickname());
        validatePassword(form.getPassword());

        Member member = Member.builder()
                .role(Role.ROLE_USER)
                .level("Seed1")
                .totalTreePoint(0L)
                .name(form.getName())
                .password(passwordEncoder.encode(form.getPassword()))
                .nickname(form.getNickname())
                .email(form.getEmail())
                .statusMessage(form.getStatusMessage())
                .build();

        memberRepository.save(member);
        redisUtil.del(VERIFIED_PREFIX + form.getEmail());

        return member.getMemberId();

    }

    public Member signInMember(SignInForm form) throws NotFoundException {
        Member findMember = memberRepository.findByName(form.getName())
                .orElseThrow(() -> new NotFoundException("계정이 존재하지 않습니다."));

        if(!passwordEncoder.matches(form.getPassword(), findMember.getPassword())) {
            throw new NotAuthenticatedException("비밀번호가 잘못되었습니다.");
        }

        return findMember;
    }

    public Member signInAdmin(SignInForm form){
        Member member = signInMember(form);

        if(!member.getRole().equals(Role.ROLE_ADMIN)) {
            throw new IllegalStateException("관리자 계정이 아닙니다.");
        }

        return member;
    }

    private void validatePassword(String password) {
        int MIN_SIZE = 8;
        int MAX_SIZE = 50;

        String regexPassword = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,50}$";
        Pattern PATTERN = Pattern.compile(regexPassword);

        if(!PATTERN.matcher(password).matches())
            throw new IllegalStateException(MIN_SIZE + "자 이상 " + MAX_SIZE + "자 이하의 숫자, 영문자, 특수문자를 포함한 비밀번호를 입력해주세요");
    }

}
