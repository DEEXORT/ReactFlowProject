package com.javarush.publisher.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.publisher.config.provider.JwtProvider;
import com.javarush.publisher.model.writer.WriterRequestTo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v2.0/login")
@RequiredArgsConstructor
@Slf4j
public class AuthRestController {
    private final ObjectMapper mapper;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> login(@RequestBody String requestBody) throws JsonProcessingException {
        log.info("requestBody: {}", requestBody);
        WriterRequestTo request = mapper.readValue(requestBody, WriterRequestTo.class);

        // User authentication
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getLogin(),
                request.getPassword()
        ));

        // Token generating
        UserDetails userDetails = (UserDetails) authenticate.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        String token = jwtProvider.generateToken(userDetails.getUsername(), roles);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(Map.of(
                        "username", userDetails.getUsername(),
                        "roles", roles,
                        "access_token", token,
                        "expires_in", jwtProvider.getExpiration(),
                        "token_type", "Bearer"
                ));
    }
}
