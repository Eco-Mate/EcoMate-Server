package com.greeny.ecomate.member.controller;

import com.greeny.ecomate.member.dto.CreateMemberRequestDto;
import com.greeny.ecomate.member.dto.MemberDto;
import com.greeny.ecomate.member.dto.UpdateMemberRequestDto;
import com.greeny.ecomate.member.service.MemberService;
import com.greeny.ecomate.utils.api.ApiUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/members")
@RequiredArgsConstructor
@Tag(name = "Member")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "사용자 생성")
    @ApiResponse(description = "사용자 생성")
    @PostMapping
    public ApiUtil.ApiSuccessResult<Long> createMember(@RequestBody CreateMemberRequestDto createDto) {
        return ApiUtil.success("사용자 생성 성공", memberService.createMember(createDto));
    }

    @Operation(summary = "사용자 정보 전체 조회")
    @GetMapping
    public ApiUtil.ApiSuccessResult<List<MemberDto>> getAllMember() {
        return ApiUtil.success("사용자 전체 조회 성공", memberService.getAllMember());
    }

    @Operation(summary = "현재 사용자 정보 조회")
    @GetMapping("/my-profile")
    public ApiUtil.ApiSuccessResult<MemberDto> getCurrentMember(HttpServletRequest req) {
        Long memberId = (Long) req.getAttribute("memberId");
        return ApiUtil.success("현재 사용자 정보 조회 성공", memberService.getCurrentMember(memberId));
    }

    @Operation(summary = "특정 사용자의 정보 조회")
    @GetMapping("/{memberId}")
    public ApiUtil.ApiSuccessResult<MemberDto> getMemberByMemberId(@PathVariable Long memberId) {
        return ApiUtil.success("특정 사용자의 정보 조회 성공", memberService.getMemberByMemberId(memberId));
    }

    @Operation(summary = "사용자 정보 수정", description = "프로필 이미지를 제외한 name, nickname, email 수정 가능")
    @PutMapping
    public ApiUtil.ApiSuccessResult<Long> updateMember(@Valid @RequestBody UpdateMemberRequestDto updateDto, HttpServletRequest req) {
        Long memberId = (Long) req.getAttribute("memberId");
        return ApiUtil.success("사용자 정보 수정 성공", memberService.updateMember(updateDto, memberId));
    }

    @Operation(summary = "사용자 프로필 이미지 수정")
    @PostMapping(value = "/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiUtil.ApiSuccessResult<Long> updateProfileImage(@RequestPart MultipartFile profileImage, HttpServletRequest req) {
        Long memberId = (Long) req.getAttribute("memberId");
        return ApiUtil.success("사용자 프로필 이미지 수정 성공", memberService.updateProfileImage(profileImage, memberId));
    }

    @Operation(summary = "사용자 프로필 이미지 삭제")
    @DeleteMapping("/profile-image")
    public ApiUtil.ApiSuccessResult<String> deleteProfileImage(HttpServletRequest req) {
        Long memberId = (Long) req.getAttribute("memberId");
        memberService.deleteProfileImage(memberId);

        return ApiUtil.success("사용자 프로필 이미지 삭제 성공", memberId + " 의 프로필 이미지가 삭제되었습니다.");
    }

}
