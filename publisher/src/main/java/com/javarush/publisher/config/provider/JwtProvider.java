package com.javarush.publisher.config.provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

public class JwtProvider {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    @Getter
    private long expiration;

    public String generateToken(String username, List<String> roles) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withSubject(username)
                .withClaim("roles", roles)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .sign(algorithm);
    }
}
