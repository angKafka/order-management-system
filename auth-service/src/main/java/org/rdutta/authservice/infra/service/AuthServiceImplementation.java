package org.rdutta.authservice.infra.service;

import lombok.RequiredArgsConstructor;
import org.rdutta.authservice.command.JwtTokenDispatcher;
import org.rdutta.authservice.infra.repository.UserRepository;
import org.rdutta.authservice.util.AuthUserMapper;
import org.rdutta.commonlibrary.dto.AuthenticationRequestDTO;
import org.rdutta.commonlibrary.dto.AuthenticationResponseDTO;
import org.rdutta.commonlibrary.dto.UserContextDTO;
import org.rdutta.commonlibrary.dto.UserRequestDTO;
import org.rdutta.commonlibrary.entity.Role;
import org.rdutta.commonlibrary.entity.Users;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImplementation implements AuthService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthUserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenDispatcher jwtTokenDispatcher;
    @Override
    public AuthenticationResponseDTO signup(UserRequestDTO userRequestDTO) {
        Users users = Users.builder()
                .name(userRequestDTO.getName())
                .email(userRequestDTO.getEmail())
                .active(userRequestDTO.isActive())
                .role(Role.USER) // Default role, can be changed later
                .password(passwordEncoder.encode(userRequestDTO.getPassword()))
                .build();

        if(userRepository.findAuthUserByEmail(userRequestDTO.getEmail()).isPresent()){
            throw new RuntimeException("User already exists with email: " + userRequestDTO.getEmail());
        }

        userRepository.save(users);

        String token = jwtService.generateToken(userMapper.fromUserToAuthUsersDetails(users));
        return AuthenticationResponseDTO.builder()
                .token(token)
                .build();
    }

    @Override
    public AuthenticationResponseDTO login(AuthenticationRequestDTO authenticationRequestDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequestDTO.email(),
                        authenticationRequestDTO.password()
                )
        );

        var user = userRepository.findAuthUserByEmail(authenticationRequestDTO.email()).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + authenticationRequestDTO.email()));
        String token = jwtService.generateToken(userMapper.fromUserToAuthUsersDetails(user));
        UserContextDTO userContextDTO = jwtService.extractUserContextFromJWT(token);
        jwtTokenDispatcher.send(userContextDTO);
        return AuthenticationResponseDTO.builder()
                .token(token)
                .build();
    }
}
