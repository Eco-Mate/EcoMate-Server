package com.greeny.ecomate.websocket.repository;

import com.greeny.ecomate.websocket.entity.ChatJoin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatJoinRepository extends JpaRepository<ChatJoin, Long> {

    List<ChatJoin> findChatJoinsByMember_MemberId(Long memberId);
    List<ChatJoin> findChatJoinsByChatRoom_RoomId(Long roomId);

}
