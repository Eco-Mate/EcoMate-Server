package com.greeny.ecomate.websocket.controller;

import com.greeny.ecomate.utils.api.ApiUtil;
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
    public ApiUtil.ApiSuccessResult<Long> createRoom(@RequestBody CreateChatRoomRequestDto dto,
                                   HttpServletRequest req) {
        Long memberId = (Long) req.getAttribute("memberId");
        return ApiUtil.success("채팅방 생성 성공", chatRoomService.createRoom(dto, memberId));
    }

    @Operation(summary = "해당 member가 속해있는 채팅방 리스트 조회", description = "account token이 필요합니다.")
    @ApiResponse(description = "채팅방 리스트 조회")
    @GetMapping("/rooms")
    public ApiUtil.ApiSuccessResult<List<ChatRoomResponseDto>> rooms(HttpServletRequest req) {
        Long memberId = (Long) req.getAttribute("memberId");
        return ApiUtil.success("채팅방 리스트 조회 성공", chatRoomService.findAllRoomByMemberId(memberId));
    }

    @Operation(summary = "채팅방 멤버 초대 시 멤버 닉네임 검색", description = "account token이 필요합니다.")
    @ApiResponse(description = "채팅방 멤버 초대 시 멤버 닉네임을 (부분)검색하여 조회")
    @GetMapping("/room/search-member")
    public ApiUtil.ApiSuccessResult<List<String>> searchMemberByNickname(@RequestParam String nickname, HttpServletRequest req) {
        return ApiUtil.success("멤버 닉네임 검색 성공", chatRoomService.searchMemberByNickname(nickname));
    }

}
