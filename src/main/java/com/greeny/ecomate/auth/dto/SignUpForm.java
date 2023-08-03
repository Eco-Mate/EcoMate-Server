package com.greeny.ecomate.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpForm {

    @NotNull(message = "아이디를 입력해주세요.")
    private String name; // 아이디
    @NotNull(message = "영문자, 숫자, 특수기호를 포함하여 최소 8자의 비밀번호를 입력해주세요.")
    private String password; // 비밀번호
    @NotNull(message = "닉네임을 입력해주세요.")
    private String nickname; // 닉네임
    @NotNull(message = "이메일을 입력해주세요.")
    private String email; // 이메일

}
