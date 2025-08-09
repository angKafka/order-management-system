package org.rdutta.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.rdutta.authservice.infra.service.AuthService;
import org.rdutta.commonlibrary.dto.AuthenticationRequestDTO;
import org.rdutta.commonlibrary.dto.AuthenticationResponseDTO;
import org.rdutta.commonlibrary.dto.UserRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auths")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponseDTO> signup(@RequestBody UserRequestDTO requestDTO){
        return ResponseEntity.ok(authService.signup(requestDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> signup(@RequestBody AuthenticationRequestDTO requestDTO){
        return ResponseEntity.ok(authService.login(requestDTO));
    }
}
