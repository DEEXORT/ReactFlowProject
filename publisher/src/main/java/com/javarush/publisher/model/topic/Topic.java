package com.javarush.publisher.model.topic;

import com.javarush.publisher.model.marker.Marker;
import com.javarush.publisher.model.writer.Writer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tbl_topic")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    @JoinColumn(name = "writer_id")
    Writer writer;

    @Column(nullable = false, unique = true)
    String title;

    @Column(nullable = false)
    String content;

    @Column
    @CreationTimestamp
    LocalDateTime created;

    @Column
    @UpdateTimestamp
    LocalDateTime modified;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "topic_marker",
            joinColumns = @JoinColumn(name = "topic_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "marker_id", referencedColumnName = "id"))
    List<Marker> markers;
}
