package com.greeny.ecomate.websocket.controller;

import com.greeny.ecomate.fcm.controller.FCMController;
import com.greeny.ecomate.fcm.service.FCMService;
import com.greeny.ecomate.websocket.dto.ChatDto;
import com.greeny.ecomate.websocket.dto.CreateChatMessageRequestDto;
import com.greeny.ecomate.websocket.entity.Chat;
import com.greeny.ecomate.websocket.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatMessageSendingController {

    private final ChatService chatService;
    private final FCMService fcmService;

    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/chat/{roomId}")
    public ChatDto sendChatMessage(@Payload CreateChatMessageRequestDto request, @DestinationVariable(value = "roomId") Long roomId, SimpMessageHeaderAccessor headerAccessor) {
        Long memberId = (Long) headerAccessor.getSessionAttributes().get("memberId");
        Chat chat = chatService.createChat(request, roomId, memberId);

        fcmService.sendChatAlarm(roomId, chat);

        return chatService.getChatById(chat.getChatId());
    }

}
