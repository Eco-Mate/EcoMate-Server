package com.greeny.ecomate.websocket.controller;

import com.greeny.ecomate.utils.api.ApiUtil;
import com.greeny.ecomate.websocket.dto.*;
import com.greeny.ecomate.websocket.service.ChatRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/v1/chat-rooms")
@RequiredArgsConstructor
@Tag(name = "ChatRoom")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @Operation(summary = "채팅방 생성", description = "account token이 필요합니다.")
    @ApiResponse(description = "채팅방 생성")
    @PostMapping("")
    public ApiUtil.ApiSuccessResult<Long> createRoom(@RequestBody CreateChatRoomRequestDto dto,
                                   HttpServletRequest req) {
        Long memberId = (Long) req.getAttribute("memberId");
        return ApiUtil.success("채팅방 생성 성공", chatRoomService.createRoom(dto, memberId));
    }

    @Operation(summary = "해당 member가 속해있는 채팅방 리스트 조회", description = "account token이 필요합니다.")
    @ApiResponse(description = "채팅방 리스트 조회")
    @GetMapping("/members")
    public ApiUtil.ApiSuccessResult<List<ChatRoomResponseDto>> rooms(HttpServletRequest req) {
        Long memberId = (Long) req.getAttribute("memberId");
        return ApiUtil.success("채팅방 리스트 조회 성공", chatRoomService.findAllRoomByMemberId(memberId));
    }

    @Operation(summary = "채팅방 내 멤버들의 챌린지 현황 조회")
    @GetMapping("/{roomId}")
    public ApiUtil.ApiSuccessResult<ChallengeStatusListDto> getChallengeStatusByChatRoom(@PathVariable Long roomId,
                                                                                         HttpServletRequest req) {
        Long memberId = (Long) req.getAttribute("memberId");
        return ApiUtil.success("채팅방 멤버들의 챌린지 현황 조회 성공", new ChallengeStatusListDto(chatRoomService.getChallengeStatusByChatRoom(roomId, memberId)));
    }

    @Operation(summary = "채팅방 멤버 초대 시 멤버 닉네임 검색", description = "account token이 필요합니다.")
    @ApiResponse(description = "채팅방 멤버 초대 시 멤버 닉네임을 (부분)검색하여 조회")
    @GetMapping("/search-members")
    public ApiUtil.ApiSuccessResult<List<String>> searchMemberByNickname(@RequestParam String nickname, HttpServletRequest req) {
        return ApiUtil.success("멤버 닉네임 검색 성공", chatRoomService.searchMemberByNickname(nickname));
    }

    @Operation(summary = "채팅방 생성 후 멤버 초대", description = "account token이 필요합니다.")
    @ApiResponse(description = "채팅방 생성 후 맴버 초대")
    @PostMapping("/{chatRoomId}")
    public ApiUtil.ApiSuccessResult<Long> addMemberToChatRoom(@PathVariable Long chatRoomId, @RequestBody MemberToChatRoomDto dto, HttpServletRequest req) {
        Long memberId = (Long) req.getAttribute("memberId");
        return ApiUtil.success("채팅방 생성 후 멤버 초대 성공", chatRoomService.addMemberToChatRoom(chatRoomId, dto, memberId));
    }
  
    @Operation(summary = "채팅방 이름 수정")
    @PutMapping("/{roomId}")
    public ApiUtil.ApiSuccessResult<Long> updateChatRoomName(@PathVariable Long roomId, @RequestBody UpdateChatRoomRequestDto updateDto,
                                                             HttpServletRequest req) {
        Long memberId = getMemberId(req);
        return ApiUtil.success("채팅방 이름 수정 성공", chatRoomService.updateChatRoomName(roomId, memberId, updateDto).getChatJoinId());
    }
  
    @Operation(summary = "채팅방 나가기", description = "account token이 필요합니다.")
    @ApiResponse(description = "채팅방 나가기")
    @DeleteMapping("/{chatRoomId}")
    public ApiUtil.ApiSuccessResult<String> leaveChatRoom(@PathVariable Long chatRoomId, HttpServletRequest req) {
        Long memberId = (Long) req.getAttribute("memberId");
        return ApiUtil.success("채팅방 나가기 성공", chatRoomService.leaveChatRoom(chatRoomId, memberId));
    }

    private Long getMemberId(HttpServletRequest req) {
        return (Long) req.getAttribute("memberId");
    }

}
