package com.javarush.reactflow.mapper;

import com.javarush.reactflow.model.reaction.Reaction;
import com.javarush.reactflow.model.reaction.ReactionRequestTo;
import com.javarush.reactflow.model.reaction.ReactionResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReactionDto {
    @Mapping(source = "topic.id", target = "topicId")
    ReactionResponseTo toDto(Reaction reaction);
    @Mapping(source = "topicId", target = "topic.id")
    Reaction fromDto(ReactionRequestTo dto);
}
