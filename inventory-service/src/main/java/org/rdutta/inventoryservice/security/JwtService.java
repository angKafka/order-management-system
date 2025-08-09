package org.rdutta.inventoryservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;

import static ch.qos.logback.core.encoder.ByteArrayUtil.hexStringToByteArray;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public UserDetails extractUserDetails(String token) {
        String username = extractUsername(token);
        String roleClaim = extractAllClaims(token).get("role", String.class);

        if (roleClaim == null || roleClaim.isBlank()) {
            // No roles found in token, create user with no roles or throw exception based on your policy
            return User.withUsername(username)
                    .password("") // no password needed here
                    .roles()      // empty roles
                    .build();
        }

        String[] roles = roleClaim.split(",");
        String[] cleanedRoles = Arrays.stream(roles)
                .map(r -> r.startsWith("ROLE_") ? r.substring(5) : r)
                .toArray(String[]::new);

        return User.withUsername(username)
                .password("")
                .roles(cleanedRoles)
                .build();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
