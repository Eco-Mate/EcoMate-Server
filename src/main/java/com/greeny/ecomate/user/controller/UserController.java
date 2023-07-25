package com.greeny.ecomate.user.controller;

import com.greeny.ecomate.user.dto.CreateUserRequestDto;
import com.greeny.ecomate.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
@Tag(name = "User")
public class UserController {

    private final UserService userService;

    @PostMapping
    public Long createUser(@RequestBody CreateUserRequestDto createDto) {
        return userService.createUser(createDto);
    }

}
