package com.javarush.publisher.model.writer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WriterResponseTo {
    Long id;
    String login;
    String password;
    String firstname;
    String lastname;
    String role;
}