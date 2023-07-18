package com.greeny.ecomate.posting.dto;

import com.greeny.ecomate.posting.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BoardListDto {
    private List<BoardDto> boardDtoList;
}
