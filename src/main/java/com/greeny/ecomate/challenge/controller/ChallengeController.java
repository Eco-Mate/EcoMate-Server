package com.greeny.ecomate.challenge.controller;

import com.greeny.ecomate.challenge.dto.ChallengeDto;
import com.greeny.ecomate.challenge.dto.CreateChallengeRequestDto;
import com.greeny.ecomate.challenge.entity.Challenge;
import com.greeny.ecomate.challenge.service.ChallengeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/v1/challenges")
@RequiredArgsConstructor
@Tag(name = "Challenge")
public class ChallengeController {

    private final ChallengeService challengeService;

    @PostMapping("/form")
    public Long createNewChallenge(@RequestBody CreateChallengeRequestDto dto) {
        return challengeService.createChallenge(dto);
    }

    @GetMapping("/{challengeId}")
    public Challenge getChallengeById(@PathVariable Long challengeId, HttpServletRequest req) {
        return challengeService.getChallengeById(challengeId);
    }

    @GetMapping
    public List<ChallengeDto> getAllChallenge() {
        return challengeService.findAllChallenge();
    }

}
