package com.star.altineller_kuyumcu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.star.altineller_kuyumcu.dto.UserDto;
import com.star.altineller_kuyumcu.dto.UserDtoIU;
import com.star.altineller_kuyumcu.dto.auth.AuthRequest;
import com.star.altineller_kuyumcu.dto.auth.AuthResponse;
import com.star.altineller_kuyumcu.dto.auth.RegisterRequest;
import com.star.altineller_kuyumcu.security.JwtService;
import com.star.altineller_kuyumcu.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.Collections;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

        private final UserService userService;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;
        private final PasswordEncoder passwordEncoder;

        @PostMapping("/register")
        public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
                UserDtoIU userDtoIU = UserDtoIU.builder()
                                .username(request.getUsername())
                                .email(request.getEmail())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .build();

                UserDto savedUser = userService.createUser(userDtoIU);
                var authorities = Collections.singletonList(
                                new SimpleGrantedAuthority("ROLE_CUSTOMER"));

                String token = jwtService.generateToken(
                                new org.springframework.security.core.userdetails.User(
                                                savedUser.getUsername(),
                                                userDtoIU.getPassword(),
                                                authorities));

                return ResponseEntity.ok(AuthResponse.builder()
                                .token(token)
                                .username(savedUser.getUsername())
                                .role(savedUser.getRole().name())
                                .build());
        }

        @PostMapping("/login")
        public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

                UserDto user = userService.getUserByUsername(request.getUsername());
                var authorities = Collections.singletonList(
                                new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

                String token = jwtService.generateToken(
                                new org.springframework.security.core.userdetails.User(
                                                user.getUsername(),
                                                request.getPassword(),
                                                authorities));

                return ResponseEntity.ok(AuthResponse.builder()
                                .token(token)
                                .username(user.getUsername())
                                .role(user.getRole().name())
                                .build());
        }
}