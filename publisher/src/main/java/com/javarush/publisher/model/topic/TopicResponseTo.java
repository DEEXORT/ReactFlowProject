package com.javarush.reactflow.model.topic;

import com.javarush.reactflow.model.marker.Marker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

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
