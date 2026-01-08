package com.javarush.reactflow.model.reaction;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReactionRequestTo {
    Long id;
    Long topicId;
    @Size(min = 2, max = 2048)
    String content;
}
