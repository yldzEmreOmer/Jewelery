package com.star.altineller_kuyumcu.security;

import org.springframework.stereotype.Service;

import com.star.altineller_kuyumcu.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderAuthorizationService {

    private final UserRepository userRepository;

    public boolean isOrderOwner(Long userId, String username) {
        return userRepository.findById(userId)
                .map(user -> user.getUsername().equals(username))
                .orElse(false);
    }
}