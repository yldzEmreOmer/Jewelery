package com.star.altineller_kuyumcu.service.impl;

import com.star.altineller_kuyumcu.model.User;
import com.star.altineller_kuyumcu.repository.UserRepository;
import com.star.altineller_kuyumcu.service.UserService;
import com.star.altineller_kuyumcu.dto.UserDto;
import com.star.altineller_kuyumcu.dto.UserDtoIU;
import com.star.altineller_kuyumcu.exception.BaseException;
import com.star.altineller_kuyumcu.exception.ResourceNotFoundException;
import com.star.altineller_kuyumcu.exception.MessageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public UserDto createUser(UserDtoIU userDtoIU) {
        if (userRepository.existsByEmail(userDtoIU.getEmail())) {
            throw new BaseException(MessageType.USER_ALREADY_EXISTS);
        }
        if (userRepository.existsByUsername(userDtoIU.getUsername())) {
            throw new BaseException(MessageType.USER_ALREADY_EXISTS);
        }
        User user = modelMapper.map(userDtoIU, User.class);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageType.USER_NOT_FOUND));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(MessageType.USER_NOT_FOUND));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(MessageType.USER_NOT_FOUND));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDto updateUser(Long id, UserDtoIU userDtoIU) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageType.USER_NOT_FOUND));

        if (!user.getEmail().equals(userDtoIU.getEmail())
                && userRepository.existsByEmail(userDtoIU.getEmail())) {
            throw new BaseException(MessageType.USER_ALREADY_EXISTS);
        }
        if (!user.getUsername().equals(userDtoIU.getUsername())
                && userRepository.existsByUsername(userDtoIU.getUsername())) {
            throw new BaseException(MessageType.USER_ALREADY_EXISTS);
        }

        modelMapper.map(userDtoIU, user);
        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserDto.class);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException(MessageType.USER_NOT_FOUND);
        }
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

}