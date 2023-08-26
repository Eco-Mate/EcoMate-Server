package com.greeny.ecomate.boardSave.controller;

import com.greeny.ecomate.board.entity.Board;
import com.greeny.ecomate.boardSave.dto.BoardSaveDto;
import com.greeny.ecomate.boardSave.dto.CreateBoardSaveRequestDto;
import com.greeny.ecomate.boardSave.service.BoardSaveService;
import com.greeny.ecomate.utils.api.ApiUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Tag(name = "BoardSave(게시물 저장)")
@RestController
@RequestMapping("/v1/board-saves")
@RequiredArgsConstructor
public class BoardSaveController {

    private final BoardSaveService boardSaveService;

    @Operation(summary = "게시물 저장")
    @PostMapping
    public ApiUtil.ApiSuccessResult<BoardSaveDto> createBoardSave(@Valid @RequestBody CreateBoardSaveRequestDto createDto,
                                                        HttpServletRequest req) {
        Long memberId = getMemberId(req);
        boardSaveService.createSaveLog(createDto, memberId);
        return ApiUtil.success("게시물 저장 성공", new BoardSaveDto(true));
    }

    @Operation(summary = "게시물 저장 취소")
    @DeleteMapping("/{boardId}")
    public ApiUtil.ApiSuccessResult<BoardSaveDto> deleteBoardSave(@PathVariable Long boardId,
                                                           HttpServletRequest req) {
        Long memberId = getMemberId(req);
        boardSaveService.deleteBoardSave(boardId, memberId);
        return ApiUtil.success("게시물 저장 취소 성공", new BoardSaveDto(false));
    }

    private Long getMemberId(HttpServletRequest req) {
        return (Long) req.getAttribute("memberId");
    }

}
