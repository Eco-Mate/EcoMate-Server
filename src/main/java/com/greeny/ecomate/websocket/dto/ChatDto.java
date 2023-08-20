package com.greeny.ecomate.websocket.dto;

import com.greeny.ecomate.member.entity.Member;
import com.greeny.ecomate.websocket.entity.Chat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessageResponseDto {
    private Long roomId;
    private String message;
    private Long senderId;
    private String senderNickname;
    private String profileImage;
    private LocalDateTime createdTime;

    public ChatMessageResponseDto(Chat chat, Member sender, String profileImageUrl) {
        this.roomId = chat.getChatRoom().getRoomId();
        this.message = chat.getMessage();
        this.senderId = chat.getSenderId();
        this.senderNickname = sender.getNickname();
        if (sender.getProfileImage() != null) {
            this.profileImage = profileImageUrl + "/" + sender.getProfileImage();
        }
        this.createdTime = chat.getCreatedDate();
    }
}
