package org.rdutta.authservice.util.filter_utils;

import org.rdutta.authservice.domain.user.AuthUsersDetails;

import java.util.Map;

public interface JWTTokenServiceActions {
    boolean isValid(String token, AuthUsersDetails usersDetails);
    boolean isExpired(String token);
    String tokenGenerator(Map<String, Object> claims, AuthUsersDetails usersDetails);
    String extractUsername(String token);
}
