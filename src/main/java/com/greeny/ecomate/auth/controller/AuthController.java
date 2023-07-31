package com.greeny.ecomate.auth.controller;

import com.greeny.ecomate.auth.dto.*;
import com.greeny.ecomate.auth.service.AuthService;
import com.greeny.ecomate.exception.CookieNotFoundException;
import com.greeny.ecomate.member.entity.Member;
import com.greeny.ecomate.security.provider.JwtProvider;
import com.greeny.ecomate.utils.api.ApiUtil;
import com.greeny.ecomate.utils.cookie.CookieUtil;
import com.greeny.ecomate.utils.redis.RedisUtil;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth")
public class AuthController {

    private final AuthService authService;
    private final JwtProvider jwtProvider;
    private final CookieUtil cookieUtil;

    private final RedisUtil redisUtil;

    @ApiResponse(description = "회원가입")
    @PostMapping(value = "/members/new", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiUtil.ApiSuccessResult<SignUpResponse> signUpMember(
            @RequestBody @Valid SignUpForm form) throws RuntimeException {
        Long id = authService.signUpMember(form);
        return ApiUtil.success("회원가입 성공", new SignUpResponse(id));
    }

    @ApiResponse(description = "로그인")
    @PostMapping(value = "/members/login", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiUtil.ApiSuccessResult<SignInResponse> signInMember(
            @RequestBody @Valid SignInForm form,
            HttpServletRequest req,
            HttpServletResponse res) throws RuntimeException {
        Member member = authService.signInMember(form);

        String accessToken = jwtProvider.generateMemberToken(member.getEmail(), member.getName() + "");
        String refreshToken = jwtProvider.generateMemberRefreshToken(member.getEmail(), member.getName() + "");

        ResponseCookie cookie = cookieUtil.createCookie(JwtProvider.ACCOUNT_TOKEN_NAME, refreshToken);
        res.addHeader("Set-Cookie", cookie.toString());

        ApiUtil.ApiSuccessResult<SignInResponse> result = ApiUtil.success(
                "로그인 성공", new SignInResponse(member, accessToken, refreshToken));

        try {
            String memberRefreshToken = cookieUtil.getCookie(req, JwtProvider.ACCOUNT_TOKEN_NAME).getValue();
            redisUtil.del(memberRefreshToken);
        } catch(CookieNotFoundException ignored) {
        } finally {
            cacheToken(accessToken, refreshToken);
        }

        return result;
    }

    @ApiResponse(description = "로그아웃")
    @GetMapping("/members/logout")
    public ApiUtil.ApiSuccessResult<String> signOutMember(
            HttpServletRequest req,
            HttpServletResponse res) {

        try {
            String memberRefreshToken = cookieUtil.getCookie(req, JwtProvider.ACCOUNT_TOKEN_NAME).getValue();
            redisUtil.del(memberRefreshToken);
        } catch (CookieNotFoundException ignored) {
        }

        ResponseCookie deleteMemberCookie = cookieUtil.createCookie(JwtProvider.ACCOUNT_TOKEN_NAME, null, 0);
        res.addHeader("Set-Cookie", deleteMemberCookie.toString());

        return ApiUtil.success("로그아웃 성공", "성공적으로 로그아웃 했습니다.");
    }

    @ApiResponse(description = "토큰 재발급")
    @PostMapping("/member-token")
    public ApiUtil.ApiSuccessResult<TokenDto> reIssueMemberToken(
            HttpServletRequest req,
            HttpServletResponse res) {
        String accessToken;
        String refreshToken;

        refreshToken = cookieUtil.getCookie(req, JwtProvider.ACCOUNT_TOKEN_NAME).getValue();

        String reIssuedRefreshToken = jwtProvider.reIssueMemberRefreshToken(refreshToken);

        if (reIssuedRefreshToken != null) {
            redisUtil.del(refreshToken);

            refreshToken = reIssuedRefreshToken;
            accessToken = jwtProvider.reIssueMemberToken(refreshToken);

            cacheToken(accessToken, refreshToken);

            ResponseCookie refreshTokenCookie = cookieUtil.createCookie(JwtProvider.ACCOUNT_TOKEN_NAME, refreshToken);

            res.addHeader("Set-Cookie", refreshTokenCookie.toString());
        } else {
            String cachedToken = (String) redisUtil.get(refreshToken);

            if (cachedToken == null) {
                accessToken = jwtProvider.reIssueMemberToken(refreshToken);
                cacheToken(accessToken, refreshToken);
            } else {
                accessToken = cachedToken;
            }
        }

        return ApiUtil.success("토큰 재발급 성공", new TokenDto(accessToken, reIssuedRefreshToken));
    }

    private void cacheToken(String accessToken, String refreshToken) {
        redisUtil.set(refreshToken, accessToken);
        redisUtil.expire(refreshToken, JwtProvider.TOKEN_CACHING_SECOND);
    }

}
