package com.greeny.ecomate.websocket.repository;

import com.greeny.ecomate.member.entity.Member;
import com.greeny.ecomate.websocket.entity.ChatJoin;
import com.greeny.ecomate.websocket.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ChatJoinRepository extends JpaRepository<ChatJoin, Long> {

    List<ChatJoin> findChatJoinsByMember_MemberId(Long memberId);
    List<ChatJoin> findChatJoinsByChatRoom_RoomId(Long roomId);

    Optional<ChatJoin> findChatJoinByMemberAndChatRoom(Member member, ChatRoom chatRoom);

    @Query("SELECT cj FROM ChatJoin cj where cj.chatRoom.roomId = :chatRoomId and cj.member.memberId = :memberId")
    Optional<ChatJoin> findChatJoinByChatRoomIdAndMemberId(Long chatRoomId, Long memberId);

    @Query("SELECT cj FROM ChatJoin cj join fetch cj.member where cj.chatRoom.roomId = :chatRoomId")
    List<ChatJoin> findChatJoinsByChatRoomId(Long chatRoomId);

    @Query("SELECT cj FROM ChatJoin cj where cj.chatRoom.roomId = :roomId and cj.member.memberId = :memberId")
    Optional<ChatJoin> findChatJoinByRoomIdAndMemberId(Long roomId, Long memberId);

}
