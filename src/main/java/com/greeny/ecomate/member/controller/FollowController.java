package com.greeny.ecomate.member.controller;

import com.greeny.ecomate.member.dto.FollowMemberDto;
import com.greeny.ecomate.member.service.FollowService;
import com.greeny.ecomate.member.service.MemberService;
import com.greeny.ecomate.utils.api.ApiUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/v1/follows")
@RequiredArgsConstructor
@Tag(name = "Follow")
public class FollowController {

    private final FollowService followService;
    private final MemberService memberService;

    @Operation(summary = "팔로우", description = "account token이 필요합니다.")
    @ApiResponse(description = "팔로우")
    @PostMapping("/{nickname}")
    public ApiUtil.ApiSuccessResult<Long> follow(@PathVariable String nickname, HttpServletRequest req) {
        System.out.println(nickname);
        Long toMemberId = memberService.getMemberByNickname(nickname).getMemberId();
        Long fromMemberId = (Long) req.getAttribute("memberId");
        return ApiUtil.success("팔로우 성공", followService.follow(fromMemberId, toMemberId));
    }

    @Operation(summary = "언팔로우", description = "account token이 필요합니다.")
    @ApiResponse(description = "언팔로우")
    @DeleteMapping("/{nickname}")
    public ApiUtil.ApiSuccessResult<String> unfollow(@PathVariable String nickname, HttpServletRequest req) {
        Long toMemberId = memberService.getMemberByNickname(nickname).getMemberId();
        Long fromMemberId = (Long) req.getAttribute("memberId");
        followService.unfollow(fromMemberId, toMemberId);
        return ApiUtil.success("언팔로우 성공", "팔로우를 취소하였습니다.");
    }

    @Operation(summary = "팔로우 상태 확인", description = "account token이 필요합니다.")
    @ApiResponse(description = "팔로우 상태 확인")
    @GetMapping("/{nickname}")
    public ApiUtil.ApiSuccessResult<Boolean> checkFollowState(@PathVariable String nickname, HttpServletRequest req) {
        Long toMemberId = memberService.getMemberByNickname(nickname).getMemberId();
        Long fromMemberId = (Long) req.getAttribute("memberId");
        return ApiUtil.success("팔로우 상태 확인 성공", followService.checkFollowState(fromMemberId, toMemberId));
    }

    @Operation(summary = "팔로잉 리스트 조회", description = "account token이 필요합니다.")
    @ApiResponse(description = "팔로잉 리스트 조회")
    @GetMapping("/followings/{nickname}")
    public ApiUtil.ApiSuccessResult<List<FollowMemberDto>> getFollowings(@PathVariable String nickname) {
        Long fromMemberId = memberService.getMemberByNickname(nickname).getMemberId();
        return ApiUtil.success("팔로잉 리스트 조회 성공", followService.getFollowings(fromMemberId));
    }

}
