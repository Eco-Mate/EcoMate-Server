package com.greeny.ecomate.posting.dto;

import com.greeny.ecomate.challenge.entity.Challenge;
import com.greeny.ecomate.posting.entity.Board;
import com.greeny.ecomate.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateBoardRequestDto {

    private String nickname;

    private Long challengeId;

    private String boardTitle;

    private String boardContent;

    private String image;

    public Board toEntity(User user) {
        return Board.builder()
                .user(user)
                .boardTitle(boardTitle)
                .boardContent(boardContent)
                .challengeId(challengeId)
                .image(image)
                .likeCnt(0L)
                .build();
    }
}
