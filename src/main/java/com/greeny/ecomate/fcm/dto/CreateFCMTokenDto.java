package com.greeny.ecomate.fcm.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateFCMTokenDto {

    @NotNull
    private String fcmToken;
}
