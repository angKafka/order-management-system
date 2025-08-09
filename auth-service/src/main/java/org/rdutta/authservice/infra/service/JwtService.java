package org.rdutta.authservice.infra.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.rdutta.authservice.domain.user.AuthUsersDetails;
import org.rdutta.authservice.util.filter_utils.ClaimsExtractor;
import org.rdutta.authservice.util.filter_utils.JWTTokenServiceActions;
import org.rdutta.authservice.util.filter_utils.SecretKeyConstants;
import org.rdutta.authservice.util.filter_utils.SecretKeyGenerator;
import org.rdutta.commonlibrary.dto.UserContextDTO;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService implements JWTTokenServiceActions, ClaimsExtractor, SecretKeyGenerator {
    @Override
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(generateKey(SecretKeyConstants.SECRET_KEY))
                .build().parseClaimsJws(token)
                .getBody();
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        final Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    @Override
    public boolean isValid(String token, AuthUsersDetails usersDetails) {
        String username = extractUsername(token);
        return (username.equals(usersDetails.getUsername()) && !isExpired(token));
    }

    @Override
    public boolean isExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    @Override
    public String tokenGenerator(Map<String, Object> claims, AuthUsersDetails usersDetails) {
        String roles = usersDetails.getAuthorities().stream()
                .map(auth -> auth.getAuthority())
                .reduce((a, b) -> a + "," + b)
                .orElse("");  // no fallback here, empty string if no roles

        claims.put("role", roles);  // store as String in claims

        return Jwts.builder().setClaims(claims)
                .setSubject(usersDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(generateKey(SecretKeyConstants.SECRET_KEY), SignatureAlgorithm.HS256)
                .compact();
    }

    public UserContextDTO extractUserContextFromJWT(String token){
        Claims claims = Jwts.parserBuilder().setSigningKey(generateKey(SecretKeyConstants.SECRET_KEY))
                .build().parseClaimsJws(token).getBody();

        String username = claims.getSubject();
        String roleStr = claims.get("role", String.class);

        Set<String> roles = new HashSet<>();
        if(roleStr != null && !roleStr.isBlank()){
            roles = Arrays.stream(roleStr.split(",")).collect(Collectors.toSet());
        }

        return new UserContextDTO(
                username,
                roles
        );
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public Key generateKey(String secret) {
        byte[] key = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(key);
    }

    public String generateToken(AuthUsersDetails userDetails){
        return tokenGenerator(new HashMap<>(), userDetails);
    }
}
