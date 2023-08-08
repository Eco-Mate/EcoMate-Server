package com.greeny.ecomate.websocket.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateChatRoomRequestDto {

    @NotNull
    private List<String> memberNicknameList;
    @NotNull
    private String name;

}
