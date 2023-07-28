package com.greeny.ecomate.auth.dto;

import com.greeny.ecomate.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInResponse {

    @NotNull
    private Long memberId;
    @NotNull
    private String accessToken;
    @NotNull
    private String refreshToken;

    public SignInResponse(Member member, String accessToken, String refreshToken) {
        this.memberId = member.getMemberId();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}
