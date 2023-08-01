package com.greeny.ecomate.challenge.controller;

import com.greeny.ecomate.challenge.dto.ChallengeDto;
import com.greeny.ecomate.challenge.dto.CreateChallengeRequestDto;
import com.greeny.ecomate.challenge.dto.UpdateChallengeRequestDto;
import com.greeny.ecomate.challenge.entity.Challenge;
import com.greeny.ecomate.challenge.service.ChallengeService;
import com.greeny.ecomate.member.entity.Member;
import com.greeny.ecomate.member.service.MemberService;
import com.greeny.ecomate.posting.dto.UpdateBoardRequestDto;
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
@RequestMapping("/v1/challenges")
@RequiredArgsConstructor
@Tag(name = "Challenge")
public class ChallengeController {

    private final ChallengeService challengeService;
    private final MemberService memberService;
    private final JwtProvider jwtProvider;

    @ApiResponse(description = "(관리자) 챌린지 생성")
    @PostMapping("/form")
    public ApiUtil.ApiSuccessResult<Long> createNewChallenge(@Valid @RequestBody CreateChallengeRequestDto dto,
                                                             HttpServletRequest req) {

        //String memberToken = JwtExtractor.extractJwt(req);
        //req.setAttribute("memberId", jwtProvider.getMemberIdFromToken(memberToken));

        Long memberId = (Long) req.getAttribute("memberId");
        Member member = memberService.getMemberById(memberId);
        Long challengeId = challengeService.createChallenge(dto, member);
        return ApiUtil.success("챌린지 생성 성공", challengeId);
    }

    @ApiResponse(description = "challengeId로 챌린지 단일 조회")
    @GetMapping("/{challengeId}")
    public ApiUtil.ApiSuccessResult<Challenge> getChallengeById(@PathVariable Long challengeId,
                                      HttpServletRequest req) {
        return ApiUtil.success("챌린지 조회 성공", challengeService.getChallengeById(challengeId));
    }

    @ApiResponse(description = "챌린지 전체 조회")
    @GetMapping
    public ApiUtil.ApiSuccessResult<List<ChallengeDto>> getAllChallenge() {
        return ApiUtil.success("챌린지 전체 조회 성공", challengeService.findAllChallenge());
    }

    @ApiResponse(description = "challengeId로 해당 챌린지를 도전하고 있는 회원 수 조회")
    @GetMapping("/cnt/{challengeId}")
    public ApiUtil.ApiSuccessResult<Long> getChallengeInCnt(@PathVariable Long challengeId) {
        return ApiUtil.success("챌린지 도전 회원 수 조회 성공", challengeService.getProceedingChallengeCnt(challengeId));
    }

    @ApiResponse(description = "(관리자) challengeId에 해당 하는 챌린지의 활성화 여부 수정")
    @PutMapping("/activeYn/{challengeId}")
    public ApiUtil.ApiSuccessResult<Long> updateChallengeActiveYn(@PathVariable Long challengeId,
                                        @RequestBody boolean activeYn,
                                        HttpServletRequest req) {

        String memberToken = JwtExtractor.extractJwt(req);
        req.setAttribute("memberId", jwtProvider.getMemberIdFromToken(memberToken));

        Long memberId = (Long) req.getAttribute("memberId");
        Member member = memberService.getMemberById(memberId);
        challengeService.updateChallengeActiveYn(challengeId, activeYn, member);
        return ApiUtil.success("챌린지 활성화 여부 수정 성공", challengeId);
    }

    @ApiResponse(description = "(관리자) challengeId에 해당하는 챌린지 수정")
    @PutMapping("/{challengeId}")
    public ApiUtil.ApiSuccessResult<Long> updateChallenge(@PathVariable Long challengeId,
                                                          @Valid @RequestBody UpdateChallengeRequestDto dto,
                                                          HttpServletRequest req) {

        String memberToken = JwtExtractor.extractJwt(req);
        req.setAttribute("memberId", jwtProvider.getMemberIdFromToken(memberToken));

        Long memberId = (Long) req.getAttribute("memberId");
        Member member = memberService.getMemberById(memberId);
        return ApiUtil.success("챌린지 수정 성공", challengeService.updateChallenge(challengeId, dto, member));
    }

    @ApiResponse(description = "(관리자) challengeId에 해당하는 챌린지 삭제")
    @DeleteMapping("/{challengeId}")
    public ApiUtil.ApiSuccessResult<String> deleteChallenge(@PathVariable Long challengeId,
                                  HttpServletRequest req) {

        String memberToken = JwtExtractor.extractJwt(req);
        req.setAttribute("memberId", jwtProvider.getMemberIdFromToken(memberToken));

        Long memberId = (Long) req.getAttribute("memberId");
        Member member = memberService.getMemberById(memberId);
        challengeService.deleteChallenge(challengeId, member);
        return ApiUtil.success("챌린지 삭제 성공", "해당 챌린지가 삭제되었습니다.");
    }

}
