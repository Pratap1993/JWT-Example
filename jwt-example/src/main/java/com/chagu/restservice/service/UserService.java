package com.chagu.restservice.service;

import com.chagu.restservice.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserDto createNewUser(UserDto userDto);

    UserDto getUserDtoByEmail(String email);

    UserDto getUserDtoByUserId(String userId);

    UserDto updateUser(String userId, UserDto dto);

    void deleteUser(String userId);

    List<UserDto> getAllUser(Integer page, Integer limit);

}
