package com.javarush.publisher.model.writer;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class WriterRequestTo {
    Long id;
    @Size(min = 2, max = 64)
    String login;
    @Size(min = 8, max = 128)
    String password;
    @Size(min = 2, max = 64)
    String firstname;
    @Size(min = 2, max = 64)
    String lastname;
    @Builder.Default
    String role = "CUSTOMER";
}
