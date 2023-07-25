package com.greeny.ecomate.user.service;

import com.greeny.ecomate.user.dto.CreateUserRequestDto;
import com.greeny.ecomate.user.dto.UserDto;
import com.greeny.ecomate.user.entity.User;
import com.greeny.ecomate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Long createUser(CreateUserRequestDto createDto) {
        return userRepository.save(createDto.toEntity()).getUserId();
    }

    public List<UserDto> getAllUser() {
        List<User> userList = userRepository.findAll();
        return userList.stream().map(UserDto::from).toList();
    }

}
