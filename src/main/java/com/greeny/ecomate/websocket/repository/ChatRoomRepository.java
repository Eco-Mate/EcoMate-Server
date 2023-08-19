package com.greeny.ecomate.websocket.repository;

import com.greeny.ecomate.websocket.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

}
