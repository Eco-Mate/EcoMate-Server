package com.greeny.ecomate.security.provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.greeny.ecomate.security.detail.MemberDetails;
import com.greeny.ecomate.security.detail.MemberDetailsService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtProvider implements AuthenticationProvider {

    private final MemberDetailsService memberDetailsService;

    // 서버 스펙 JWT 유효기간
    private static final long TOKEN_VALIDATION_SECOND = 1000L * 60 * 120;
    private static final long REFRESH_TOKEN_VALIDATION_TIME = 1000L * 60 * 60 * 48;

    // 인증 토큰 캐싱 유효기간
    public static final long TOKEN_CACHING_SECOND = 60L * 119;

    // 서버 스펙 JWT 이름
    public static final String ACCOUNT_TOKEN_NAME = "account_refresh_token";


    @Value("${spring.jwt.secret}")
    private String SECRET_KEY;

    @Value("${group.name}")
    private String ISSUER;

    private Algorithm getSigningKey(String secretKey) {
        return Algorithm.HMAC256(secretKey);
    }

    private Map<String, Claim> getAllClaims(DecodedJWT token) {
        return token.getClaims();
    }

    public String getEmailFromToken(String token) {
        DecodedJWT verifiedToken = validateToken(token, getMemberTokenValidator());
        String result = verifiedToken.getClaim("email").asString();
        return result;
    }

    public Long getMemberIdFromToken(String token) {
        DecodedJWT verifiedToken = validateToken(token, getMemberTokenValidator());
        String result = verifiedToken.getClaim("member_id").asString();
        return Long.parseLong(result);
    }

    private JWTVerifier getMemberTokenValidator() {
        return JWT.require(getSigningKey(SECRET_KEY))
                .withClaimPresence("email")
                .withClaimPresence("member_id")
                .withIssuer(ISSUER)
                .build();
    }

    private JWTVerifier getMemberRefreshTokenValidator() {
        return JWT.require(getSigningKey(SECRET_KEY))
                .withClaimPresence("email")
                .withClaimPresence("member_id")
                .acceptExpiresAt(REFRESH_TOKEN_VALIDATION_TIME)
                .withIssuer(ISSUER)
                .build();
    }

    public String generateMemberToken(String email, String memberId) {
        Map<String, String> payload = new HashMap<>();
        payload.put("email", email);
        payload.put("member_id", memberId);
        return doGenerateToken(TOKEN_VALIDATION_SECOND, payload);
    }

    public String generateMemberRefreshToken(String email, String memberId) {
        Map<String, String> payload = new HashMap<>();
        payload.put("email", email);
        payload.put("member_id", memberId);
        return doGenerateToken(REFRESH_TOKEN_VALIDATION_TIME, payload);
    }

    public String reIssueMemberToken(String refreshToken) {
        DecodedJWT decodedJWT = validateToken(refreshToken, getMemberRefreshTokenValidator());

        String email = decodedJWT.getClaim("email").asString();
        String memberId = decodedJWT.getClaim("member_id").asString();

        return generateMemberToken(email, memberId);
    }

    public String reIssueMemberRefreshToken(String refreshToken) {
        // 아예 invalid 한 경우(refresh 토큰이 아니거나 만료된 경우에는 예외)
        try {
            validateToken(refreshToken, getMemberTokenValidator());
        } catch (InvalidClaimException e) {
            throw new IllegalStateException("토큰이 올바르지 않습니다.");
        } catch (TokenExpiredException e) {
            try {
                DecodedJWT decodedJWT = validateToken(refreshToken, getMemberRefreshTokenValidator());
                String email = decodedJWT.getClaim("email").asString();
                String memberId = decodedJWT.getClaim("member_id").asString();
                return generateMemberRefreshToken(email, memberId);
            } catch (TokenExpiredException e2) {
                throw new IllegalStateException("토큰이 만료되었습니다.");
            }
        }
        return null;
    }

    private String doGenerateToken(long expireTime, Map<String, String> payload) {

        return JWT.create()
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + expireTime))
                .withPayload(payload)
                .withIssuer(ISSUER)
                .sign(getSigningKey(SECRET_KEY));
    }

    private DecodedJWT validateToken(String token, JWTVerifier validator) throws JWTVerificationException {
        return validator.verify(token);
    }

    public boolean isMemberTokenExpired(String token) {
        try {
            validateToken(token, getMemberTokenValidator());
            return false;
        } catch (JWTVerificationException e) {
            return true;
        }
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MemberDetails userDetails;

        if (authentication.getCredentials().equals(Strings.EMPTY)) {
            userDetails = (MemberDetails) memberDetailsService
                    .loadUserByUsername((String) authentication.getPrincipal());
        } else {
            userDetails = (MemberDetails) memberDetailsService
                    .loadUserByUsername(authentication.getCredentials().toString());
        }

        return new UsernamePasswordAuthenticationToken(
                userDetails.getEmail(),
                userDetails.getPassword(),
                userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }


}
