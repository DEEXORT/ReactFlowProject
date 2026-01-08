package com.javarush.reactflow.model.marker;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MarkerRequestTo {
    Long id;
    @Size(min = 2, max = 32)
    String name;
}
