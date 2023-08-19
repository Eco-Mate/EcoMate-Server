package com.greeny.ecomate.websocket.controller;

import com.greeny.ecomate.websocket.dto.ChatRoomResponseDto;
import com.greeny.ecomate.websocket.dto.CreateChatRoomRequestDto;
import com.greeny.ecomate.websocket.service.ChatRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/v1/chat")
@RequiredArgsConstructor
@Tag(name = "ChatRoom")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @Operation(summary = "채팅방 생성", description = "account token이 필요합니다.")
    @ApiResponse(description = "채팅방 생성")
    @PostMapping("/room")
    public Long createRoom(@RequestBody CreateChatRoomRequestDto dto,
                               HttpServletRequest req) {
        Long memberId = (Long) req.getAttribute("memberId");
        return chatRoomService.createRoom(dto, memberId);
    }

    @Operation(summary = "해당 member가 속해있는 채팅방 리스트 조회", description = "account token이 필요합니다.")
    @ApiResponse(description = "채팅방 리스트 조회")
    @GetMapping("/rooms")
    public List<ChatRoomResponseDto> rooms(HttpServletRequest req) {
        Long memberId = (Long) req.getAttribute("memberId");
        return chatRoomService.findAllRoomByMemberId(memberId);
    }

}
