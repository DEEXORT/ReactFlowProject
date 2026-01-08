package com.javarush.publisher.model.marker;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MarkerResponseTo {
    Long id;
    String name;
}
