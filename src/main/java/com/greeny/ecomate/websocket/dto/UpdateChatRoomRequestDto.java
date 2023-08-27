package com.greeny.ecomate.websocket.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateChatRoomRequestDto {
    @NotNull
    private String roomName;
}
