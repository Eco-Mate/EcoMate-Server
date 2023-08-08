package com.greeny.ecomate.websocket.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChatRoomResponseDto {

    private Long roomId;
    private List<String> memberNicknameList;
    private String name;

    public ChatRoomResponseDto(Long roomId, List<String> memberNicknameList, String name) {
        this.roomId = roomId;
        this.memberNicknameList = memberNicknameList;
        this.name = name;
    }

}
