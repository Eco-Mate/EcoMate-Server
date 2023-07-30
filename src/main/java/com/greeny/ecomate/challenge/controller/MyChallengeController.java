package com.greeny.ecomate.challenge.controller;

import com.greeny.ecomate.challenge.dto.CreateMyChallengeRequestDto;
import com.greeny.ecomate.challenge.dto.MyChallengeDto;
import com.greeny.ecomate.challenge.service.MyChallengeService;
import com.greeny.ecomate.member.entity.Member;
import com.greeny.ecomate.member.service.MemberService;
import com.greeny.ecomate.security.provider.JwtProvider;
import com.greeny.ecomate.utils.api.ApiUtil;
import com.greeny.ecomate.utils.jwt.JwtExtractor;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/myChallenges")
@RequiredArgsConstructor
@Tag(name = "MyChallenge")
public class MyChallengeController {

    private final MyChallengeService myChallengeService;
    private final MemberService memberService;
    private final JwtProvider jwtProvider;

    @ApiResponse(description = "챌린지 새도전 or 재도전")
    @PostMapping("/{challengeId}")
    public ApiUtil.ApiSuccessResult<Long> createMyChallenge(@PathVariable Long challengeId,
                                                            HttpServletRequest req) {
        String memberToken = JwtExtractor.extractJwt(req);
        req.setAttribute("memberId", jwtProvider.getMemberIdFromToken(memberToken));

        Long memberId = (Long) req.getAttribute("memberId");
        Long myChallengeId = myChallengeService.createMyChallenge(challengeId, memberId);
        return ApiUtil.success("챌린지 도전 시작 성공", myChallengeId);
    }

    @ApiResponse(description = "memberId 별 도전 챌린지 전체 조회")
    @GetMapping("/member/all")
    public ApiUtil.ApiSuccessResult<List<MyChallengeDto>> getAllMyChallengeByMemberId(HttpServletRequest req) {
        String memberToken = JwtExtractor.extractJwt(req);
        req.setAttribute("memberId", jwtProvider.getMemberIdFromToken(memberToken));

        Long memberId = (Long) req.getAttribute("memberId");

        return ApiUtil.success("사용자별 도전 챌린지 조회 성공", myChallengeService.getAllMyChallengeByMemberId(memberId));
    }

    @ApiResponse(description = "memberId 별 진행 중인 챌린지 전체 조회")
    @GetMapping("/member/proceeding")
    public ApiUtil.ApiSuccessResult<List<MyChallengeDto>> getAllMyChallengeProceedingByMemberId(HttpServletRequest req) {
        String memberToken = JwtExtractor.extractJwt(req);
        req.setAttribute("memberId", jwtProvider.getMemberIdFromToken(memberToken));

        Long memberId = (Long) req.getAttribute("memberId");
        return ApiUtil.success("사용자별 진행 중인 챌린지 전체 조회 성공", myChallengeService.getAllMyChallengeProceedingByMemberId(memberId));
    }

    @ApiResponse(description = "memberId 별 완료한 챌린지 전체 조회")
    @GetMapping("/member/finish")
    public ApiUtil.ApiSuccessResult<List<MyChallengeDto>> getAllMyChallengeDoneByMemberId(HttpServletRequest req) {
        String memberToken = JwtExtractor.extractJwt(req);
        req.setAttribute("memberId", jwtProvider.getMemberIdFromToken(memberToken));

        Long memberId = (Long) req.getAttribute("memberId");
        return ApiUtil.success("사용자별 완료한 챌린지 전체 조회 성공", myChallengeService.getAllMyChallengeFinishByMemberId(memberId));
    }

    @ApiResponse(description = "memberId에 해당하는 사용자가 진행 중인 챌린지 수 조회")
    @GetMapping("/member/proceeding/cnt")
    public ApiUtil.ApiSuccessResult<Long> getMyChallengeProceedingCntByMemberId(HttpServletRequest req) {
        String memberToken = JwtExtractor.extractJwt(req);
        req.setAttribute("memberId", jwtProvider.getMemberIdFromToken(memberToken));

        Long memberId = (Long) req.getAttribute("memberId");
        return ApiUtil.success("해당 사용자가 도전 중인 챌린지 수 조회 성공", myChallengeService.getMyChallengeProceedingCntByMemberId(memberId));
    }

    @ApiResponse(description = "memberId에 해당하는 사용자가 완료한 챌린지 수 조회")
    @GetMapping("/member/finish/cnt")
    public ApiUtil.ApiSuccessResult<Long> getMyChallengeDoneCntByMemberId(HttpServletRequest req) {
        String memberToken = JwtExtractor.extractJwt(req);
        req.setAttribute("memberId", jwtProvider.getMemberIdFromToken(memberToken));

        Long memberId = (Long) req.getAttribute("memberId");
        return ApiUtil.success("해당 사용자가 도전 완료한 챌린지 수 조회 성공", myChallengeService.getMyChallengeFinishCntByMemberId(memberId));
    }

    @ApiResponse(description = "myChallengeId로 도전 챌린지 단일 조회")
    @GetMapping("/{myChallengeId}")
    public ApiUtil.ApiSuccessResult<MyChallengeDto> getMyChallengeById(@PathVariable Long myChallengeId) {
        return ApiUtil.success("도전 챌린지 단일 조회 성공", myChallengeService.getMyChallengeById(myChallengeId));
    }

    @ApiResponse(description = "myChallengeId로 해당 챌린지 도전에 대한 인증 횟수 수정")
    @PutMapping("/{myChallengeId}")
    public ApiUtil.ApiSuccessResult<String> updateMyChallengeDoneCnt(@PathVariable Long myChallengeId,
                                                                     HttpServletRequest req) {
        String memberToken = JwtExtractor.extractJwt(req);
        req.setAttribute("memberId", jwtProvider.getMemberIdFromToken(memberToken));

        Long memberId = (Long) req.getAttribute("memberId");
        String message = myChallengeService.updateMyChallengeDoneCnt(myChallengeId, memberId);
        return ApiUtil.success("챌린지 도전에 대한 인증 횟수 수정 성공", message);
    }

    @ApiResponse(description = "myChallengeId로 해당 도전 챌린지 삭제 (포기)")
    @DeleteMapping("/{myChallengeId}")
    public ApiUtil.ApiSuccessResult<String> deleteMyChallenge(@PathVariable Long myChallengeId,
                                                              HttpServletRequest req) {
        String memberToken = JwtExtractor.extractJwt(req);
        req.setAttribute("memberId", jwtProvider.getMemberIdFromToken(memberToken));

        Long memberId = (Long) req.getAttribute("memberId");
        myChallengeService.deleteMyChallenge(myChallengeId, memberId);
        return ApiUtil.success("도전 챌린지 삭제 성공", "해당 챌린지를 포기하였습니다.");
    }

}
