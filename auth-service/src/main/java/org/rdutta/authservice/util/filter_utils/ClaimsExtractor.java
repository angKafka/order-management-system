package org.rdutta.authservice.util.filter_utils;

import io.jsonwebtoken.Claims;

import java.util.function.Function;

public interface ClaimsExtractor {
    Claims extractAllClaims(String token);
    <T> T extractClaim(String token, Function<Claims, T> resolver);
}
