package com.greeny.ecomate.posting.controller;

import com.greeny.ecomate.posting.dto.BoardDto;
import com.greeny.ecomate.posting.dto.CreateBoardRequestDto;
import com.greeny.ecomate.posting.dto.UpdateBoardRequestDto;
import com.greeny.ecomate.posting.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/board")
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public Long createBoard(@RequestBody CreateBoardRequestDto createDto) {
        return boardService.createBoard(createDto);
    }

    @GetMapping
    public List<BoardDto> getAllBoard() {
        return boardService.getAllBoard();
    }

    @PutMapping
    public Long updateBoard(@RequestBody UpdateBoardRequestDto updateDto) {
        return boardService.updateBoard(updateDto);
    }

}
