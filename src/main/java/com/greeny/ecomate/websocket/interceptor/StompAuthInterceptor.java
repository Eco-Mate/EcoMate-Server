package com.greeny.ecomate.websocket.interceptor;

import com.greeny.ecomate.exception.NotFoundException;
import com.greeny.ecomate.exception.websocket.IllegalWebSocketRequestException;
import com.greeny.ecomate.member.entity.Member;
import com.greeny.ecomate.member.repository.MemberRepository;
import com.greeny.ecomate.security.provider.JwtProvider;
import com.greeny.ecomate.websocket.entity.ChatJoin;
import com.greeny.ecomate.websocket.entity.ChatRoom;
import com.greeny.ecomate.websocket.repository.ChatJoinRepository;
import com.greeny.ecomate.websocket.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class StompAuthInterceptor implements ChannelInterceptor {

    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;
    private final ChatJoinRepository chatJoinRepository;
    private final ChatRoomRepository chatRoomRepository;

    private final String CHAT_SUB_PREFIX = "/topic/chat/";
    private final String CHAT_PUB_PREFIX = "/pub/chat/";

    @Override
    @Transactional
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();

        if (StompCommand.CONNECT.equals(headerAccessor.getCommand())) {
            // memberId 세션에 저장
            Long memberId = getMemberIdFromHeader(headerAccessor);

            assert sessionAttributes != null;
            sessionAttributes.put("memberId", memberId);
        }
        else if (StompCommand.SUBSCRIBE.equals(headerAccessor.getCommand())) {
            validateDestination(headerAccessor, CHAT_SUB_PREFIX);

            // 웹소켓 접속 상태 업데이트
            Long memberId = (Long) headerAccessor.getSessionAttributes().get("memberId");
            Long reqRoomId = Long.parseLong(headerAccessor.getDestination().substring(CHAT_SUB_PREFIX.length()));
            ChatJoin chatJoin = chatJoinRepository.findChatJoinByRoomIdAndMemberId(reqRoomId, memberId)
                    .orElseThrow(() -> new IllegalWebSocketRequestException("올바르지 않은 요청입니다."));

            chatJoin.updateActiveYn(true);
        }
        else if (StompCommand.SEND.equals(headerAccessor.getCommand())) {
            validateDestination(headerAccessor, CHAT_PUB_PREFIX);
        }

        return message;
    }

    private Long getMemberIdFromHeader(StompHeaderAccessor headerAccessor) {
        List<String> authorization = headerAccessor.getNativeHeader("Authorization");
        if (authorization == null || authorization.size() != 1) {
            throw new IllegalWebSocketRequestException("해당 요청에서 헤더의 인증 정보가 올바르지 않습니다.");
        }

        Long memberId = jwtProvider.getMemberIdFromToken(authorization.get(0));
        memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다."));

        return memberId;
    }

    private void validateDestination(StompHeaderAccessor headerAccessor, String chatPrefix) {
        Long memberId = (Long) headerAccessor.getSessionAttributes().get("memberId");
        String destination = headerAccessor.getDestination();

        if (destination == null || !destination.startsWith(chatPrefix)) {
            throw new IllegalArgumentException("올바르지 않은 요청 형식입니다.");
        }

        Long reqRoomId = Long.parseLong(destination.substring(chatPrefix.length()));
        if (chatJoinRepository.findChatJoinByChatRoomIdAndMemberId(reqRoomId, memberId).isEmpty()) {
            throw new IllegalWebSocketRequestException("올바르지 않은 요청입니다.");
        }
    }
}
