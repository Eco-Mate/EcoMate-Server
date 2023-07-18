package com.greeny.ecomate.posting.controller;

import com.greeny.ecomate.posting.dto.BoardDto;
import com.greeny.ecomate.posting.dto.BoardListDto;
import com.greeny.ecomate.posting.dto.CreateBoardRequestDto;
import com.greeny.ecomate.posting.dto.UpdateBoardRequestDto;
import com.greeny.ecomate.posting.service.BoardService;
import com.greeny.ecomate.utils.api.ApiUtil;
import com.greeny.ecomate.utils.api.ApiUtil.ApiSuccessResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/board")
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ApiUtil.ApiSuccessResult<Long> createBoard(@RequestBody CreateBoardRequestDto createDto) {
        return ApiUtil.success("게시판 생성 성공", boardService.createBoard(createDto));
    }

    @GetMapping
    public ApiUtil.ApiSuccessResult<BoardListDto> getAllBoard() {
        return ApiUtil.success("게시판 전체 조회 성공", new BoardListDto(boardService.getAllBoard()));
    }

    @PutMapping
    public ApiUtil.ApiSuccessResult<Long> updateBoard(@RequestBody UpdateBoardRequestDto updateDto) {
        return ApiUtil.success("게시판 수정 성공", boardService.updateBoard(updateDto));
    }

}
