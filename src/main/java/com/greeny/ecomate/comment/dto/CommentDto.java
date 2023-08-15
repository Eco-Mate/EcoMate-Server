package com.greeny.ecomate.comment.dto;

import com.greeny.ecomate.comment.entity.Comment;
import com.greeny.ecomate.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {

    private Long commentId;
    private Long memberId;
    private String nickname;
    private String profileImage;
    private String content;
    private LocalDateTime createdDate;

    public CommentDto(Member member, Comment comment) {
        this.commentId = comment.getCommentId();
        this.memberId = member.getMemberId();
        this.nickname = member.getNickname();
        this.profileImage = member.getProfileImage();
        this.content = comment.getContent();
        this.createdDate = comment.getCreatedDate();
    }

}
