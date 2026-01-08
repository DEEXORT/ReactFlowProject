package com.javarush.discussion.reaction.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;

@Table("tbl_reaction")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reaction implements Serializable {

    @PrimaryKey
    ReactionKey reactionKey;

    @Column("content")
    String content;

//    State state;
//
//    public enum State {
//        PENDING, APPROVE, DELCINE
//    }
}
