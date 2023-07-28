package com.greeny.ecomate.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpForm {

    @NotNull
    private String name; // 아이디
    @NotNull
    private String password; // 비밀번호
    @NotNull
    private String nickname; // 닉네임
    @NotNull
    private String email; // 이메일

}
