package com.star.altineller_kuyumcu.service;

import com.star.altineller_kuyumcu.dto.UserDto;
import com.star.altineller_kuyumcu.dto.UserDtoIU;
import java.util.List;

public interface UserService {
    UserDto createUser(UserDtoIU userDtoIU);

    UserDto getUserById(Long id);

    UserDto getUserByEmail(String email);

    UserDto getUserByUsername(String username);

    List<UserDto> getAllUsers();

    UserDto updateUser(Long id, UserDtoIU userDtoIU);

    void deleteUser(Long id);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}