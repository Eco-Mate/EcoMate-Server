package com.greeny.ecomate.auth.service;

import com.greeny.ecomate.auth.dto.SignUpForm;
import com.greeny.ecomate.exception.NotFoundException;
import com.greeny.ecomate.user.entity.User;
import com.greeny.ecomate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class authService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private boolean isDuplicateUserName(String name) {
        return userRepository.existsUserByName(name);
    }

    private boolean isDuplicateUserNickname(String nickname) {
        return userRepository.exitsUserByNickname(nickname);
    }

    private boolean isDuplicateUserEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    @Transactional
    public Long signUp(SignUpForm form) throws RuntimeException {
        if(isDuplicateUserName(form.getName())) {
            throw new IllegalStateException("이미 존재하는 아이디 입니다.");
        }
        if(isDuplicateUserNickname(form.getNickname())) {
            throw new IllegalStateException("이미 존재하는 닉네임 입니다.");
        }
        if(isDuplicateUserEmail(form.getEmail())) {
            throw new IllegalStateException("이미 존재하는 이메일 입니다.");
        }

        User user = User.builder()
                .name(form.getName())
                .password(passwordEncoder.encode(form.getPassword()))
                .nickname(form.getNickname())
                .email(form.getEmail())
                .build();

        userRepository.save(user);

        return user.getUserId();
    }

    public User signIn(SignUpForm form) throws NotFoundException {
        User findUser = userRepository.findByName(form.getName())
                .orElseThrow(() -> new NotFoundException("계정이 존재하지 않습니다."));

        if(!passwordEncoder.matches(form.getPassword(), findUser.getPassword())) {
            throw new NotFoundException("비밀번호가 잘못되었습니다.");
        }

        return findUser;
    }

}
