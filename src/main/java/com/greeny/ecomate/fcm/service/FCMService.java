package com.greeny.ecomate.fcm.service;

import com.google.firebase.messaging.*;
import com.greeny.ecomate.exception.NotFoundException;
import com.greeny.ecomate.exception.firebase.FirebaseMessageException;
import com.greeny.ecomate.fcm.dto.CreateFCMTokenDto;
import com.greeny.ecomate.member.entity.Member;
import com.greeny.ecomate.member.repository.MemberRepository;
import com.greeny.ecomate.websocket.entity.Chat;
import com.greeny.ecomate.websocket.entity.ChatJoin;
import com.greeny.ecomate.websocket.repository.ChatJoinRepository;
import com.greeny.ecomate.websocket.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ExecutionException;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FCMService {

    private final MemberRepository memberRepository;
    private final ChatJoinRepository chatJoinRepository;


    @Transactional
    public Member registerFCMToken(CreateFCMTokenDto createDto, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다."));
        member.updateFCMToken(createDto.getFcmToken());

        return member;
    }

}
