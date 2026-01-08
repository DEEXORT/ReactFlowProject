package com.javarush.reactflow.model.reaction;

import com.javarush.reactflow.model.topic.Topic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_reaction")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    @JoinColumn(name = "topic_id")
    Topic topic;

    @Column(nullable = false)
    String content;

//    @Transient
//    State state;
//
//    public enum State {
//        PENDING, APPROVE, DELCINE
//    }
}
