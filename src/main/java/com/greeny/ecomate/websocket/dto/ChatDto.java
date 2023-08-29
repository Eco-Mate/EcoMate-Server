package com.greeny.ecomate.websocket.dto;

import com.greeny.ecomate.member.entity.Member;
import com.greeny.ecomate.utils.imageUtil.ImageUtil;
import com.greeny.ecomate.websocket.entity.Chat;
import com.greeny.ecomate.websocket.entity.ChatType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatDto {
    private Long chatId;
    private String message;
    private ChatType chatType;
    private Long senderId;
    private String senderNickname;
    private String profileImage;
    private LocalDateTime createdTime;

    public ChatDto(Chat chat, Member sender) {
        this.chatId = chat.getChatId();
        this.message = chat.getMessage();
        this.chatType = chat.getChatType();
        this.senderId = chat.getSenderId();
        this.senderNickname = sender.getNickname();
        this.profileImage = ImageUtil.getProfileImage(sender.getProfileImage());
        this.createdTime = chat.getCreatedDate();
    }
}
