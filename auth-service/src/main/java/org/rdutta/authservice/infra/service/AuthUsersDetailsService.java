package org.rdutta.authservice.infra.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUsersDetailsService implements UserDetailsService {
    private final AuthUserService authUserService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return authUserService.loadAuthUsersByUsername(username);
    }
}
