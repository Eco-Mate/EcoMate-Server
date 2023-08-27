package com.greeny.ecomate.websocket.service;

import com.greeny.ecomate.exception.NotFoundException;
import com.greeny.ecomate.member.entity.Member;
import com.greeny.ecomate.member.repository.MemberRepository;
import com.greeny.ecomate.websocket.dto.ChatRoomResponseDto;
import com.greeny.ecomate.websocket.dto.CreateChatRoomRequestDto;
import com.greeny.ecomate.websocket.dto.MemberToChatRoomDto;
import com.greeny.ecomate.websocket.dto.UpdateChatRoomRequestDto;
import com.greeny.ecomate.websocket.entity.ChatJoin;
import com.greeny.ecomate.websocket.entity.ChatRoom;
import com.greeny.ecomate.websocket.repository.ChatJoinRepository;
import com.greeny.ecomate.websocket.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatJoinRepository chatJoinRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createRoom(CreateChatRoomRequestDto dto, Long memberId) {
        ChatRoom chatRoom = ChatRoom.builder()
                .name(dto.getName())
                .build();
        Long chatRoomId = chatRoomRepository.save(chatRoom).getRoomId();

        List<String> memberNicknameList = dto.getMemberNicknameList();
        for(int i = 0; i < memberNicknameList.size(); i++) {
            Member member = memberRepository.findByNickname(memberNicknameList.get(i))
                    .orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다."));
            ChatJoin chatJoin = ChatJoin.builder()
                            .member(member)
                            .chatRoom(chatRoom)
                            .build();
            chatJoinRepository.save(chatJoin);
        }

        return chatRoomId;
    }

    public List<ChatRoomResponseDto> findAllRoomByMemberId(Long memberId) {
        List<ChatRoom> chatJoinRooms = chatJoinRepository.findChatJoinsByMember_MemberId(memberId).stream().map(ChatJoin::getChatRoom).toList();
        List<ChatRoomResponseDto> chatRoomList = new ArrayList<>();
        for(int i = 0; i < chatJoinRooms.size(); i++) {
            ChatRoom chatRoom = chatJoinRooms.get(i);
            List<Member> chatRoomJoinMemberList = chatJoinRepository.findChatJoinsByChatRoom_RoomId(chatRoom.getRoomId()).stream().map(ChatJoin::getMember).toList();
            List<String> memberNicknameList = chatRoomJoinMemberList.stream().map(Member::getNickname).toList();
            chatRoomList.add(createChatRoomResponseDto(chatRoom.getRoomId(), memberNicknameList, chatRoom.getName()));
        }
        return chatRoomList;
    }

    public ChatRoomResponseDto createChatRoomResponseDto(Long roomId, List<String> memberNicknameList, String name) {
        return new ChatRoomResponseDto(roomId, memberNicknameList, name);
    }

    @Transactional(readOnly = true)
    public List<String> searchMemberByNickname(String nickname) {
        List<String> nicknameList = memberRepository.findByNicknameContaining(nickname).stream().map(Member::getNickname).toList();
        return nicknameList;
    }

    @Transactional
    public Long addMemberToChatRoom(Long chatRoomId, MemberToChatRoomDto dto, Long memberId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 채팅방입니다."));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다."));

        ChatJoin chatJoin = chatJoinRepository.findChatJoinByMemberAndChatRoom(member, chatRoom)
                .orElseThrow(() -> new IllegalArgumentException("멤버 초대 권한이 없습니다."));

        List<Member> originMemberList = chatJoinRepository.findChatJoinsByChatRoom_RoomId(chatRoomId).stream().map(ChatJoin::getMember).toList();
        List<String> originMemberNicknameList = originMemberList.stream().map(Member::getNickname).toList();
        List<String> nicknameList = dto.getMemberNicknameList();

        for(int i = 0; i < nicknameList.size(); i++) {
            if(!originMemberNicknameList.contains(nicknameList.get(i))) {
                Member addMember = memberRepository.findByNickname(nicknameList.get(i))
                        .orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다."));
                ChatJoin newChatJoin = ChatJoin.builder()
                        .member(addMember)
                        .chatRoom(chatRoom)
                        .build();
                chatJoinRepository.save(newChatJoin);
            }
        }

        return chatRoomId;
    }

    public ChatJoin updateChatRoomName(Long roomId, Long memberId, UpdateChatRoomRequestDto updateDto) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 채팅방입니다."));

        ChatJoin chatJoin = chatJoinRepository.findChatJoinByChatRoomIdAndMemberId(roomId, memberId)
                .orElseThrow(() -> new NotFoundException("해당 채팅방에 참여하고 있지 않습니다."));
        chatRoom.updateRoomName(updateDto.getRoomName());

        return chatJoin;
    }
}
