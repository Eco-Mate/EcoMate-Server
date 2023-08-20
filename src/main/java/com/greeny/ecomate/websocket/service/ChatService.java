package com.greeny.ecomate.websocket.service;

import com.greeny.ecomate.exception.NotFoundException;
import com.greeny.ecomate.exception.UnauthorizedAccessException;
import com.greeny.ecomate.member.entity.Member;
import com.greeny.ecomate.member.repository.MemberRepository;
import com.greeny.ecomate.websocket.dto.ChatDto;
import com.greeny.ecomate.websocket.dto.CreateChatMessageRequestDto;
import com.greeny.ecomate.websocket.entity.Chat;
import com.greeny.ecomate.websocket.entity.ChatRoom;
import com.greeny.ecomate.websocket.entity.ChatType;
import com.greeny.ecomate.websocket.repository.ChatJoinRepository;
import com.greeny.ecomate.websocket.repository.ChatRepository;
import com.greeny.ecomate.websocket.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    @Value("${s3-directory.profile}")
    String profileDirectory;

    @Value("${cloud.aws.s3.url}")
    String s3Url;

    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatJoinRepository chatJoinRepository;
    private final MemberRepository memberRepository;

    public Chat createChat(CreateChatMessageRequestDto createDto, Long roomId, Long memberId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 채팅방입니다."));

        Chat chat = Chat.builder()
                .chatRoom(chatRoom)
                .senderId(memberId)
                .message(createDto.getMessage())
                .chatType(ChatType.CHAT)
                .build();

        return chatRepository.save(chat);
    }

    public ChatDto getChatById(Long chatId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 채팅 메시지입니다."));
        Member member = memberRepository.findById(chat.getSenderId())
                .orElseThrow(() -> new NotFoundException("채팅 발신자가 존재하지 않는 사용자입니다."));
        return new ChatDto(chat, member, s3Url + "/" + profileDirectory);
    }

    public List<ChatDto> getAllByRoom(Long roomId, Long memberId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 채팅방입니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다."));

        if (chatJoinRepository.findChatJoinByMemberAndChatRoom(member, chatRoom).isEmpty()) {
            throw new UnauthorizedAccessException("해당 채팅방에 접근 권한이 없습니다.");
        }

        List<Chat> chatList = chatRepository.findAllByChatRoom(chatRoom);

        return chatList.stream().map(this::createChatDto).toList();
    }

    private ChatDto createChatDto(Chat chat) {
        Member sender = memberRepository.findById(chat.getSenderId())
                .orElseThrow(() -> new NotFoundException("채팅 발신자가 존재하지 않는 사용자입니다."));

        return new ChatDto(chat, sender, s3Url + "/" + profileDirectory);
    }
}
