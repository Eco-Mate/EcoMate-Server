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
public class ChatRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "name")
    private String name;    // 채팅방 이름

    @Builder
    public ChatRoom(String name) {
        this.name = name;
    }

}
