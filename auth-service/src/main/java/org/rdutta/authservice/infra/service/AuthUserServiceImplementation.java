package org.rdutta.authservice.infra.service;

import lombok.RequiredArgsConstructor;
import org.rdutta.authservice.domain.user.AuthUsersDetails;
import org.rdutta.authservice.infra.repository.UserRepository;
import org.rdutta.authservice.util.AuthUserMapper;
import org.rdutta.commonlibrary.entity.Users;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUserServiceImplementation implements AuthUserService{
    private final UserRepository userRepository;
    private final AuthUserMapper mapper;
    @Override
    public AuthUsersDetails loadAuthUsersByUsername(String email) {
        Users users = userRepository.findAuthUserByEmail(email).orElseThrow(()->new UsernameNotFoundException("User not found."));
        return mapper.fromUserToAuthUsersDetails(users);
    }
}
