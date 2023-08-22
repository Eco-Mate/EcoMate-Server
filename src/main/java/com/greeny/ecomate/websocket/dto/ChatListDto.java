package com.greeny.ecomate.websocket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ChatListDto {
    private List<ChatDto> chatList;
}
