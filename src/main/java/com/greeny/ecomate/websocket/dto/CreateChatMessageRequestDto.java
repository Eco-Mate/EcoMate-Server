package com.greeny.ecomate.websocket.dto;

import com.greeny.ecomate.websocket.entity.ChatType;
import lombok.Data;

import java.util.List;

@Data
public class CreateChatMessageRequestDto {
    private String message;
    private ChatType chatType;
    private List<String> memberNicknameList;
}
