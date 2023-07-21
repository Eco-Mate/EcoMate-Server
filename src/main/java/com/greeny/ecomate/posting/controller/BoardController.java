package com.greeny.ecomate.posting.controller;

import com.greeny.ecomate.posting.dto.BoardDto;
import com.greeny.ecomate.posting.dto.BoardListDto;
import com.greeny.ecomate.posting.dto.CreateBoardRequestDto;
import com.greeny.ecomate.posting.dto.UpdateBoardRequestDto;
import com.greeny.ecomate.posting.service.BoardService;
import com.greeny.ecomate.utils.api.ApiUtil;
import com.greeny.ecomate.utils.api.ApiUtil.ApiSuccessResult;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Board(게시물)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/board")
public class BoardController {

    private final BoardService boardService;

    @PostMapping

    public ApiUtil.ApiSuccessResult<Long> createBoard(@RequestPart CreateBoardRequestDto createDto, @RequestPart MultipartFile file) {
        return ApiUtil.success("게시물 생성 성공", boardService.createBoard(createDto, file));
    }

    @GetMapping
    public ApiUtil.ApiSuccessResult<BoardListDto> getAllBoard() {
        return ApiUtil.success("게시물 전체 조회 성공", new BoardListDto(boardService.getAllBoard()));
    }

    @PutMapping
    public ApiUtil.ApiSuccessResult<Long> updateBoard(@Valid @RequestBody UpdateBoardRequestDto updateDto) {
        return ApiUtil.success("게시물 수정 성공", boardService.updateBoard(updateDto));
    }

}
