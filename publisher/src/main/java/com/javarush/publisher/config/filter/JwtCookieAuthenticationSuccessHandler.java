package com.javarush.publisher.config.filter;

import com.javarush.publisher.config.provider.JwtProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtCookieAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtProvider jwtProvider;
    private final String cookieName = "access_token";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        String token = jwtProvider.generateToken(userDetails.getUsername(), roles);

        Cookie cookie = new Cookie(cookieName, token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(request.isSecure());
        response.addCookie(cookie);

        response.sendRedirect("/api/v2.0/writers");
    }
}
