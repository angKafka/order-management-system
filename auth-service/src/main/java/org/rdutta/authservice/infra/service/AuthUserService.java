package org.rdutta.authservice.infra.service;

import org.rdutta.authservice.domain.user.AuthUsersDetails;

public interface AuthUserService {
    AuthUsersDetails loadAuthUsersByUsername(String email);
}
