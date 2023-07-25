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
    private String name;
    @NotNull
    private String password;
    @NotNull
    private String nickname;
    @NotNull
    private String email;

}
