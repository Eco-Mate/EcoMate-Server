package com.greeny.ecomate.websocket.listener;

import com.greeny.ecomate.websocket.entity.ChatJoin;
import com.greeny.ecomate.websocket.repository.ChatJoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final ChatJoinRepository chatJoinRepository;

    @EventListener
    @Transactional
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        List<ChatJoin> activeList = chatJoinRepository.findChatJoinByMemberIdAndActiveYn((Long) headerAccessor.getSessionAttributes().get("memberId"), true);

        activeList.forEach(chatJoin -> chatJoin.updateActiveYn(false));
    }
}
