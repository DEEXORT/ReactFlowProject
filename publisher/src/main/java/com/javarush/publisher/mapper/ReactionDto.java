package com.javarush.publisher.mapper;

import com.javarush.publisher.model.reaction.Reaction;
import com.javarush.publisher.model.reaction.ReactionRequestTo;
import com.javarush.publisher.model.reaction.ReactionResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReactionDto {
    @Mapping(source = "topic.id", target = "topicId")
    ReactionResponseTo toDto(Reaction reaction);
    @Mapping(source = "topicId", target = "topic.id")
    Reaction fromDto(ReactionRequestTo dto);
}
