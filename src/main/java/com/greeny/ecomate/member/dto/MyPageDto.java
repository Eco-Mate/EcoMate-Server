package com.greeny.ecomate.member.dto;

import com.greeny.ecomate.board.dto.BoardListDto;
import com.greeny.ecomate.challenge.dto.MyChallengeDto;
import lombok.Data;

import java.util.List;

@Data
public class MyPageDto {

    private BoardListDto boardList;
    private List<MyChallengeDto> challengeList;

}
