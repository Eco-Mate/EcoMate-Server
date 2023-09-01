package com.greeny.ecomate.websocket.entity;

import com.greeny.ecomate.base.BaseEntity;
import com.greeny.ecomate.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatJoin extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_join_id")
    private Long chatJoinId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;

    @Column(name = "active_yn")
    private Boolean activeYn;

    @Builder
    public ChatJoin(Member member, ChatRoom chatRoom) {
        this.member = member;
        this.chatRoom = chatRoom;
        this.activeYn = false;
    }

    public void updateActiveYn(Boolean activeYn) {
        this.activeYn = activeYn;
    }
}
