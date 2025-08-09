package org.rdutta.authservice.infra.service;

import org.rdutta.commonlibrary.dto.AuthenticationRequestDTO;
import org.rdutta.commonlibrary.dto.AuthenticationResponseDTO;
import org.rdutta.commonlibrary.dto.UserRequestDTO;

public interface AuthService {
    AuthenticationResponseDTO signup(UserRequestDTO userRequestDTO);
    AuthenticationResponseDTO login(AuthenticationRequestDTO authenticationRequestDTO);
}
