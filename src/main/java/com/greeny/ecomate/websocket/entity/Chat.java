package com.greeny.ecomate.websocket.entity;

import com.greeny.ecomate.base.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long chatId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;

    private ChatType chatType;

    @Column(name = "sender")
    private Long senderId;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    @Builder
    public Chat(ChatRoom chatRoom, ChatType chatType, Long senderId, String message) {
        this.chatRoom = chatRoom;
        this.chatType = chatType;
        this.senderId = senderId;
        this.message = message;
    }


}
