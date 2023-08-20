package com.greeny.ecomate.board.controller;

import com.greeny.ecomate.board.dto.BoardListDto;
import com.greeny.ecomate.board.dto.CreateBoardRequestDto;
import com.greeny.ecomate.board.dto.UpdateBoardRequestDto;
import com.greeny.ecomate.board.service.BoardService;
import com.greeny.ecomate.utils.api.ApiUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Tag(name = "Board(게시물)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/boards")
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "게시물 생성")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ApiUtil.ApiSuccessResult<Long> createBoard(@Valid @RequestPart CreateBoardRequestDto createDto, @RequestPart MultipartFile file,
                                                      HttpServletRequest req) {
        Long memberId = (Long) req.getAttribute("memberId");
        return ApiUtil.success("게시물 생성 성공", boardService.createBoard(createDto, file, memberId).getBoardId());
    }

    @Operation(summary = "게시물 전체 조회", description = "challengeTitle == null : 챌린지 미등록 게시물, profileImage == null : 기본 프로필 이미지 입니다.")
    @GetMapping
    public ApiUtil.ApiSuccessResult<BoardListDto> getAllBoard(HttpServletRequest req) {
        Long memberId = (Long) req.getAttribute("memberId");
        return ApiUtil.success("게시물 전체 조회 성공", new BoardListDto(boardService.getAllBoard(memberId)));
    }

    @Operation(summary = "인기 게시물 조회", description = "challengeTitle == null : 챌린지 미등록 게시물, profileImage == null : 기본 프로필 이미지 입니다.")
    @GetMapping("/popular-posts")
    public ApiUtil.ApiSuccessResult<BoardListDto> getAllSortedByLikeCnt(HttpServletRequest req) {
        Long memberId = (Long) req.getAttribute("memberId");
        return ApiUtil.success("인기 게시물 조회 성공", new BoardListDto(boardService.getAllSortedByLikeCnt(memberId)));
    }

    @Operation(summary = "게시물 수정", description = "boardTitle, boardContent 만 수정 가능합니다.")
    @PutMapping("/{boardId}")
    public ApiUtil.ApiSuccessResult<Long> updateBoard(@PathVariable Long boardId, @Valid @RequestBody UpdateBoardRequestDto updateDto,
                                                      HttpServletRequest req) {
        Long memberId = (Long) req.getAttribute("memberId");
        return ApiUtil.success("게시물 수정 성공", boardService.updateBoard(boardId, memberId, updateDto));
    }

    @Operation(summary = "게시물 삭제")
    @DeleteMapping("/{boardId}")
    public ApiUtil.ApiSuccessResult<String> deleteBoardById(@PathVariable Long boardId,
                                                            HttpServletRequest req) {
        Long memberId = (Long) req.getAttribute("memberId");
        boardService.deleteBoardById(boardId, memberId);
        return ApiUtil.success("게시물 삭제 성공", boardId + " 이(가) 삭제되었습니다.");
    }

}
