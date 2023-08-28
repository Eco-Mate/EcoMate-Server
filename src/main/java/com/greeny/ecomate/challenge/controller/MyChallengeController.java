package com.greeny.ecomate.challenge.controller;

import com.greeny.ecomate.challenge.dto.MyChallengeDto;
import com.greeny.ecomate.challenge.service.MyChallengeService;
import com.greeny.ecomate.utils.api.ApiUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/v1/myChallenges")
@RequiredArgsConstructor
@Tag(name = "MyChallenge")
public class MyChallengeController {

    private final MyChallengeService myChallengeService;

    @Operation(summary = "로그인된 사용자의 챌린지 새도전 or 재도전", description = "account token이 필요합니다.")
    @ApiResponse(description = "챌린지 새도전 or 재도전")
    @PostMapping("/{challengeId}")
    public ApiUtil.ApiSuccessResult<Long> createMyChallenge(@PathVariable Long challengeId,
                                                            HttpServletRequest req) {
        Long memberId = (Long) req.getAttribute("memberId");
        Long myChallengeId = myChallengeService.createMyChallenge(challengeId, memberId);
        return ApiUtil.success("챌린지 도전 시작 성공", myChallengeId);
    }

    @Operation(summary = "로그인된 사용자의 도전 챌린지 전체 조회", description = "account token이 필요합니다.")
    @ApiResponse(description = "로그인된 사용자의 도전 챌린지 전체 조회")
    @GetMapping("/member/all")
    public ApiUtil.ApiSuccessResult<List<MyChallengeDto>> getAllMyChallenges(HttpServletRequest req) {
        Long memberId = (Long) req.getAttribute("memberId");
        return ApiUtil.success("로그인된 사용자의 도전 챌린지 조회 성공", myChallengeService.getAllMyChallengeByMemberId(memberId));
    }

    @Operation(summary = "로그인된 사용자의 진행 중인 챌린지 전체 조회", description = "account token이 필요합니다.")
    @ApiResponse(description = "로그인된 사용자의 진행 중인 챌린지 전체 조회")
    @GetMapping("/member/proceeding")
    public ApiUtil.ApiSuccessResult<List<MyChallengeDto>> getAllMyChallengesProceeding(HttpServletRequest req) {
        Long memberId = (Long) req.getAttribute("memberId");
        return ApiUtil.success("로그인된 사용자의 진행 중인 챌린지 전체 조회 성공", myChallengeService.getAllMyChallengeProceedingByMemberId(memberId));
    }

    @Operation(summary = "로그인된 사용자의 완료한 챌린지 전체 조회", description = "account token이 필요합니다.")
    @ApiResponse(description = "로그인된 사용자의 완료한 챌린지 전체 조회")
    @GetMapping("/member/finish")
    public ApiUtil.ApiSuccessResult<List<MyChallengeDto>> getAllMyChallengesDone(HttpServletRequest req) {
        Long memberId = (Long) req.getAttribute("memberId");
        return ApiUtil.success("로그인된 사용자의 완료한 챌린지 전체 조회 성공", myChallengeService.getAllMyChallengeFinishByMemberId(memberId));
    }

    @Operation(summary = "로그인된 사용자의 진행 중인 챌린지 수 조회", description = "account token이 필요합니다.")
    @ApiResponse(description = "로그인된 사용자의 진행 중인 챌린지 수 조회")
    @GetMapping("/member/proceeding/cnt")
    public ApiUtil.ApiSuccessResult<Long> getMyChallengeProceedingCnt(HttpServletRequest req) {
        Long memberId = (Long) req.getAttribute("memberId");
        return ApiUtil.success("로그인된 사용자가 도전 중인 챌린지 수 조회 성공", myChallengeService.getMyChallengeProceedingCntByMemberId(memberId));
    }

    @Operation(summary = "로그인된 사용자의 완료한 챌린지 수 조회", description = "account token이 필요합니다.")
    @ApiResponse(description = "로그인된 사용자의 완료한 챌린지 수 조회")
    @GetMapping("/member/finish/cnt")
    public ApiUtil.ApiSuccessResult<Long> getMyChallengeDoneCnt(HttpServletRequest req) {
        Long memberId = (Long) req.getAttribute("memberId");
        return ApiUtil.success("로그인된 사용자가 도전 완료한 챌린지 수 조회 성공", myChallengeService.getMyChallengeFinishCntByMemberId(memberId));
    }

    @Operation(summary = "memberId에 해당하는 사용자의 진행 중인 챌린지 전체 조회")
    @ApiResponse(description = "memberId에 해당하는 사용자의 진행 중인 챌린지 전체 조회")
    @GetMapping("/member/{memberId}/proceeding")
    public ApiUtil.ApiSuccessResult<List<MyChallengeDto>> getAllMyChallengesProceedingByMemberId(@PathVariable Long memberId) {
        return ApiUtil.success("해당 사용자의 진행 중인 챌린지 전제 조회 성공", myChallengeService.getAllMyChallengeProceedingByMemberId(memberId));
    }

    @Operation(summary = "memberId에 해당하는 사용자의 진행 중이거나 완료한 챌린지 전체 조회")
    @ApiResponse(description = "memberId에 해당하는 사용자의 진행 중이거나 완료한 챌린지 전체 조회")
    @GetMapping("/member/{memberId}/all")
    public ApiUtil.ApiSuccessResult<List<MyChallengeDto>> getAllMyChallengesByMemberId(@PathVariable Long memberId) {
        return ApiUtil.success("해당 사용자의 진행 중이거나 완료한 챌린지 전체 조회 성공", myChallengeService.getAllMyChallengeByMemberId(memberId));
    }

    @Operation(summary = "myChallengeId로 도전 챌린지 단일 조회")
    @ApiResponse(description = "myChallengeId로 도전 챌린지 단일 조회")
    @GetMapping("/{myChallengeId}")
    public ApiUtil.ApiSuccessResult<MyChallengeDto> getMyChallengeById(@PathVariable Long myChallengeId) {
        return ApiUtil.success("도전 챌린지 단일 조회 성공", myChallengeService.getMyChallengeById(myChallengeId));
    }

    @Operation(summary = "myChallengeId로 해당 챌린지 도전에 대한 인증 횟수 수정", description = "account token이 필요합니다.")
    @ApiResponse(description = "myChallengeId로 해당 챌린지 도전에 대한 인증 횟수 수정")
    @PutMapping("/{myChallengeId}")
    public ApiUtil.ApiSuccessResult<String> updateMyChallengeDoneCnt(@PathVariable Long myChallengeId,
                                                                     HttpServletRequest req) {
        Long memberId = (Long) req.getAttribute("memberId");
        String message = myChallengeService.updateMyChallengeDoneCnt(myChallengeId, memberId);
        return ApiUtil.success("챌린지 도전에 대한 인증 횟수 수정 성공", message);
    }

    @Operation(summary = "myChallengeId로 해당 도전 챌린지 삭제 (포기)", description = "account token이 필요합니다.")
    @ApiResponse(description = "myChallengeId로 해당 도전 챌린지 삭제 (포기)")
    @DeleteMapping("/{myChallengeId}")
    public ApiUtil.ApiSuccessResult<String> deleteMyChallenge(@PathVariable Long myChallengeId,
                                                              HttpServletRequest req) {
        Long memberId = (Long) req.getAttribute("memberId");
        myChallengeService.deleteMyChallenge(myChallengeId, memberId);
        return ApiUtil.success("도전 챌린지 삭제 성공", "해당 챌린지를 포기하였습니다.");
    }

}
