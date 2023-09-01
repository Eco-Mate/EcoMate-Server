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

    @Transactional
    public void sendChatAlarm(Long roomId, Chat chat) {
        List<ChatJoin> chatJoinList = chatJoinRepository.findChatJoinsByChatRoomId(roomId);

        chatJoinList.stream()
                .filter(chatJoin -> !chatJoin.getActiveYn())
                .filter(chatJoin -> chatJoin.getMember().getFcmToken() != null)
                .forEach(chatJoin -> {
                    try {
                        sendAlarm(chatJoin.getMember().getFcmToken(), chat);
                    } catch (ExecutionException | InterruptedException e) {
                        throw new FirebaseMessageException("FCM 알림 전송에 실패했습니다.");
                    }
                });
    }

    private void sendAlarm(String fcmToken, Chat chat) throws ExecutionException, InterruptedException {
        Member sender = memberRepository.findById(chat.getSenderId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다."));

        Message message = Message.builder()
                .setToken(fcmToken)
                .setNotification(new Notification(sender.getNickname(), chat.getMessage()))
                .build();

        String response = FirebaseMessaging.getInstance().sendAsync(message).get();

    }

}
