package com.greeny.ecomate.user.controller;

import com.greeny.ecomate.user.dto.CreateUserRequestDto;
import com.greeny.ecomate.user.service.UserService;
import com.greeny.ecomate.utils.api.ApiUtil;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
@Tag(name = "User")
public class UserController {

    private final UserService userService;

    @ApiResponse(description = "사용자 생성")
    @PostMapping
    public ApiUtil.ApiSuccessResult<Long> createUser(@RequestBody CreateUserRequestDto createDto) {
        return ApiUtil.success("사용자 생성 성공", userService.createUser(createDto));
    }

}
