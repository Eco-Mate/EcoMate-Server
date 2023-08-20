package com.greeny.ecomate.websocket.repository;

import com.greeny.ecomate.websocket.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
