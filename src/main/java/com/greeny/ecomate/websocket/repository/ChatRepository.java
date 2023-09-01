package com.greeny.ecomate.websocket.repository;

import com.greeny.ecomate.websocket.entity.Chat;
import com.greeny.ecomate.websocket.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("SELECT c FROM Chat c where c.chatRoom.roomId = :roomId")
    List<Chat> findAllByRoomId(Long roomId);
}
