package com.javarush.publisher.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.publisher.model.writer.WriterRequestTo;
import com.javarush.publisher.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v2.0/login")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final ObjectMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void login(@RequestBody String requestBody) throws JsonProcessingException {
        log.info("requestBody: {}", requestBody);
        WriterRequestTo request = mapper.readValue(requestBody, WriterRequestTo.class);
        boolean authenticate = authService.authenticate(request.getLogin(), request.getPassword());
        log.info("authenticate: {}", authenticate);
        if (!authenticate) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
}
