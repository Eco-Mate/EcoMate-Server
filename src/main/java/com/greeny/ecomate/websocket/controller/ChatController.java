package com.greeny.ecomate.websocket.controller;

import com.greeny.ecomate.utils.api.ApiUtil;
import com.greeny.ecomate.websocket.dto.ChatDto;
import com.greeny.ecomate.websocket.dto.ChatListDto;
import com.greeny.ecomate.websocket.service.ChatService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/chats")
@Tag(name = "Chatting")
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/rooms/{roomId}")
    public ApiUtil.ApiSuccessResult<ChatListDto> getAllByRoom(@PathVariable Long roomId,
                                   HttpServletRequest req) {
        Long memberId = (Long) req.getAttribute("memberId");
        return ApiUtil.success("채팅방의 채팅 기록 조회 성공", new ChatListDto(chatService.getAllByRoom(roomId, memberId)));
    }
}
