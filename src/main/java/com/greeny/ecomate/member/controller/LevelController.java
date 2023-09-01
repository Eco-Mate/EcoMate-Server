package com.greeny.ecomate.member.controller;

import com.greeny.ecomate.member.dto.CreateLevelRequestDto;
import com.greeny.ecomate.member.dto.LevelDto;
import com.greeny.ecomate.member.dto.UpdateLevelRequestDto;
import com.greeny.ecomate.member.service.LevelService;
import com.greeny.ecomate.utils.api.ApiUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/v1/levels")
@RequiredArgsConstructor
@Tag(name = "Level")
public class LevelController {

    private final LevelService levelService;

    @Operation(summary = "레벨 생성")
    @ApiResponse(description = "레벨 생성")
    @PostMapping
    public ApiUtil.ApiSuccessResult<Long> createLevel(@Valid @RequestBody CreateLevelRequestDto createDto, HttpServletRequest req) {
        Long memberId = getMemberId(req);
        return ApiUtil.success("레벨 생성 성공", levelService.createLevel(createDto, memberId));
    }

    @Operation(summary = "레벨 이름으로 조회")
    @ApiResponse(description = "레벨 이름으로 조회")
    @GetMapping("/{levelName}")
    public ApiUtil.ApiSuccessResult<LevelDto> getLevelByLevelName(@PathVariable String levelName) {
        return ApiUtil.success("레벨 이름으로 조회 성공", levelService.getLevel(levelName));
    }

    @Operation(summary = "레벨 수정")
    @ApiResponse(description = "레벨 수정")
    @PutMapping("/{levelId}")
    public ApiUtil.ApiSuccessResult<Long> updateLevel(@PathVariable Long levelId, @RequestBody UpdateLevelRequestDto dto, HttpServletRequest req) {
        Long memberId = getMemberId(req);
        return ApiUtil.success("레벨 수정 성공", levelService.updateLevel(levelId, dto, memberId));
    }

    private Long getMemberId(HttpServletRequest req) {
        return (Long) req.getAttribute("memberId");
    }

}
