package com.greeny.ecomate.user.service;

import com.greeny.ecomate.user.dto.CreateUserRequestDto;
import com.greeny.ecomate.user.entity.User;
import com.greeny.ecomate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Long createUser(CreateUserRequestDto createDto) {
        return userRepository.save(createDto.toEntity()).getUserId();
    }

}
