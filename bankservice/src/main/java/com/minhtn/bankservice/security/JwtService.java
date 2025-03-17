package com.minhtn.bankservice.security;

import com.minhtn.bankservice.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    @Value("${jwt.expired}")
    private long JWT_ACCESS_TOKEN_EXPIRATION;

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaim(token, claims -> claims.get("username", String.class));
    }

    public String generateToken(User user) {
        Map<String, Object> extraClams = new HashMap<>();
        extraClams.put("username", user.getUsername());
        return generateToken(extraClams);
    }

    public String generateToken(Map<String, Object> extraClams) {
        long jwtTimeToLive = JWT_ACCESS_TOKEN_EXPIRATION * 24 * 60 * 60 * 1000;
        return Jwts.builder()
                .claims(extraClams)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtTimeToLive))
                .signWith(getSigningKey())
                .compact();
    }

    public boolean isTokenValid(String token, User user) {
        String username = extractUsername(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
    }
}
