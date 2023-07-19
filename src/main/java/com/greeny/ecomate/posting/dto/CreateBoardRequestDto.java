package com.greeny.ecomate.posting.dto;

import com.greeny.ecomate.challenge.entity.Challenge;
import com.greeny.ecomate.posting.entity.Board;
import com.greeny.ecomate.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class CreateBoardRequestDto {

    @NotNull
    private String nickname;

    @NotNull
    private Long challengeId;

    @NotNull
    private String boardTitle;

    @NotNull
    private String boardContent;

    @NotNull
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
