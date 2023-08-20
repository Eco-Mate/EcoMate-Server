package com.greeny.ecomate.member.controller;

import com.greeny.ecomate.member.dto.CreateMemberRequestDto;
import com.greeny.ecomate.member.dto.MemberDto;
import com.greeny.ecomate.member.service.MemberService;
import com.greeny.ecomate.utils.api.ApiUtil;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/v1/members")
@RequiredArgsConstructor
@Tag(name = "Member")
public class MemberController {

    private final MemberService memberService;

    @ApiResponse(description = "사용자 생성")
    @PostMapping
    public ApiUtil.ApiSuccessResult<Long> createMember(@RequestBody CreateMemberRequestDto createDto) {
        return ApiUtil.success("사용자 생성 성공", memberService.createMember(createDto));
    }

    @GetMapping
    public ApiUtil.ApiSuccessResult<List<MemberDto>> getAllMember() {
        return ApiUtil.success("사용자 전체 조회 성공", memberService.getAllMember());
    }

    @GetMapping("/my-profile")
    public ApiUtil.ApiSuccessResult<MemberDto> getCurrentMember(HttpServletRequest req) {
        Long memberId = (Long) req.getAttribute("memberId");
        return ApiUtil.success("현재 사용자 정보 조회 성공", memberService.getCurrentMember(memberId));
    }

}
