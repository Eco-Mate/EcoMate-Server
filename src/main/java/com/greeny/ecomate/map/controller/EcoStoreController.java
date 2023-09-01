package com.greeny.ecomate.map.controller;

import com.greeny.ecomate.map.dto.CreateEcoStoreRequestDto;
import com.greeny.ecomate.map.dto.EcoStoreDto;
import com.greeny.ecomate.map.dto.UpdateEcoStoreRequestDto;
import com.greeny.ecomate.map.service.EcoStoreService;
import com.greeny.ecomate.utils.api.ApiUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/v1/ecoStores")
@RequiredArgsConstructor
@Tag(name = "EcoStore")
public class EcoStoreController {

    private final EcoStoreService ecoStoreService;

    @Operation(summary = "에코 매장 생성")
    @ApiResponse(description = "에코 매장 생성")
    @PostMapping
    public ApiUtil.ApiSuccessResult<Long> createEcoStore(@Valid @RequestBody CreateEcoStoreRequestDto createDto, HttpServletRequest req) {
        Long memberId = getMemberId(req);
        return ApiUtil.success("에코 매장 생성 성공", ecoStoreService.createEcoStore(createDto, memberId));
    }

    @Operation(summary = "에코 매장 생성")
    @ApiResponse(description = "에코 매장 생성")
    @GetMapping("/{storeId}")
    public ApiUtil.ApiSuccessResult<EcoStoreDto> getEcoStoreById(@PathVariable Long storeId) {
        return ApiUtil.success("에코 매장 조회 성공", ecoStoreService.getEcoStoreById(storeId));
    }

    @Operation(summary = "에코 매장 수정")
    @ApiResponse(description = "에코 매장 수정")
    @PutMapping("/{storeId}")
    public ApiUtil.ApiSuccessResult<Long> updateEcoStore(@PathVariable Long storeId, @RequestBody UpdateEcoStoreRequestDto dto, HttpServletRequest req) {
        Long memberId = getMemberId(req);
        return ApiUtil.success("에코 매장 수정 성공", ecoStoreService.updateEcoStore(storeId, dto, memberId));
    }

    private Long getMemberId(HttpServletRequest req) {
        return (Long) req.getAttribute("memberId");
    }

}
