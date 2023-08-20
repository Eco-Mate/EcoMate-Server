package com.greeny.ecomate.websocket.service;

import com.greeny.ecomate.exception.NotFoundException;
import com.greeny.ecomate.member.entity.Member;
import com.greeny.ecomate.member.repository.MemberRepository;
import com.greeny.ecomate.websocket.dto.ChatRoomResponseDto;
import com.greeny.ecomate.websocket.dto.CreateChatRoomRequestDto;
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
        List<ChatJoin> chatJoinList = chatJoinRepository.findChatJoinsByMember_MemberId(memberId);
        List<ChatRoomResponseDto> chatRoomList = new ArrayList<>();
        for(int i = 0; i < chatJoinList.size(); i++) {
            ChatRoom chatRoom = chatJoinList.get(i).getChatRoom();
            List<ChatJoin> chatRoomJoinList = chatJoinRepository.findChatJoinsByChatRoom_RoomId(chatRoom.getRoomId());
            List<String> memberNicknameList = new ArrayList<>();
            for(int j = 0; j < chatRoomJoinList.size(); j++)
                memberNicknameList.add(chatRoomJoinList.get(j).getMember().getNickname());
            chatRoomList.add(createChatRoomResponseDto(chatRoom.getRoomId(), memberNicknameList, chatJoinList.get(i).getChatRoom().getName()));
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

}
