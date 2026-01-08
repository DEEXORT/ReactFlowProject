package com.javarush.publisher.config.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/// Проверяет JWT-токен при запросах
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${cookie.name.token}")
    private String cookieName;

    public JwtAuthenticationFilter() {
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token;
        token = getJwtTokenFromHeader(request);
        if (token == null) token = getJwtTokenFromCookie(request);

        if (token != null) {
            try {
                Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT jwt = verifier.verify(token);

                List<String> roles = jwt.getClaim("roles").asList(String.class);
                List<SimpleGrantedAuthority> authorityList = roles
                        .stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .toList();

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(jwt.getSubject(), null, authorityList);

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } catch (JWTVerificationException ex) {
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtTokenFromHeader(HttpServletRequest request) {
        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                return authHeader.substring(7);
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    private String getJwtTokenFromCookie(HttpServletRequest request) {
        try {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        } catch (Exception ignored) {}
        return null;
    }
}
