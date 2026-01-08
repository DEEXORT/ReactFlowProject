package com.javarush.publisher.model.topic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopicResponseTo {
    Long id;
    Long writerId;
    String title;
    String content;
    LocalDateTime created;
    LocalDateTime modified;
    String[] markers;
}
