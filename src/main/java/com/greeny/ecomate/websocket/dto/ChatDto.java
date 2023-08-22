package com.greeny.ecomate.websocket.dto;

import com.greeny.ecomate.member.entity.Member;
import com.greeny.ecomate.websocket.entity.Chat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatDto {
    private Long chatId;
    private String message;
    private Long senderId;
    private String senderNickname;
    private String profileImage;
    private LocalDateTime createdTime;

    public ChatDto(Chat chat, Member sender, String profileImageUrl) {
        this.chatId = chat.getChatId();
        this.message = chat.getMessage();
        this.senderId = chat.getSenderId();
        this.senderNickname = sender.getNickname();
        if (sender.getProfileImage() != null) {
            this.profileImage = profileImageUrl + "/" + sender.getProfileImage();
        }
        this.createdTime = chat.getCreatedDate();
    }
}
