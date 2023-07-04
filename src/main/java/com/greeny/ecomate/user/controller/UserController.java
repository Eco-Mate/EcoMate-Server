package com.greeny.ecomate.user.controller;

import com.greeny.ecomate.user.dto.CreateUserRequestDto;
import com.greeny.ecomate.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public Long createUser(@RequestBody CreateUserRequestDto createDto) {
        return userService.createUser(createDto);
    }

}
