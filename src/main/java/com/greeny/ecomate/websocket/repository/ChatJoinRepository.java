package com.greeny.ecomate.websocket.repository;

import com.greeny.ecomate.member.entity.Member;
import com.greeny.ecomate.websocket.entity.ChatJoin;
import com.greeny.ecomate.websocket.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatJoinRepository extends JpaRepository<ChatJoin, Long> {

    List<ChatJoin> findChatJoinsByMember_MemberId(Long memberId);
    List<ChatJoin> findChatJoinsByChatRoom_RoomId(Long roomId);

    Optional<ChatJoin> findChatJoinByMemberAndChatRoom(Member member, ChatRoom chatRoom);

}
