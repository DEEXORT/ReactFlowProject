package com.javarush.publisher.model.marker;

import com.javarush.publisher.model.topic.Topic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tbl_marker")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Marker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true)
    String name;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "topic_marker",
            joinColumns = @JoinColumn(name = "marker_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "topic_id", referencedColumnName = "id"))
    List<Topic> topics;
}
