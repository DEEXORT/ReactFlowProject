package com.javarush.discussion.reaction;

import com.javarush.discussion.reaction.model.Reaction;
import com.javarush.discussion.reaction.model.ReactionRequestTo;
import com.javarush.discussion.reaction.model.ReactionResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ReactionDto {
    @Mapping(source = "reactionKey.id", target = "id")
    @Mapping(source = "reactionKey.topicId", target = "topicId")
    ReactionResponseTo toDto(Reaction reaction);

    @Mapping(target = "reactionKey.id", source = "id")
    @Mapping(target = "reactionKey.topicId", source = "topicId")
    @Mapping(target = "reactionKey.country", ignore = true)
    Reaction fromDto(ReactionRequestTo dto);
}
