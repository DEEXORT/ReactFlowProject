package com.javarush.discussion.reaction.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReactionKey {

    @PrimaryKeyColumn(value = "country", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    String country;

    @PrimaryKeyColumn(value = "id", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    Long id;

    @PrimaryKeyColumn(value = "topic_id", ordinal = 2, type = PrimaryKeyType.CLUSTERED)
    Long topicId;
}
