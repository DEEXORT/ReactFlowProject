package com.javarush.publisher.model.reaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReactionResponseTo {
    Long id;
    Long topicId;
    String content;
}
