package com.javarush.discussion.reaction;

import com.javarush.discussion.reaction.model.Reaction;
import com.javarush.discussion.reaction.model.ReactionKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.Optional;

public interface ReactionRepository extends CassandraRepository<Reaction, ReactionKey> {
    @Query("SELECT * FROM tbl_reaction WHERE country = ?0 AND id = ?1")
    Optional<Reaction> findByCountryAndId(String country, Long id);

    @Query("DELETE FROM tbl_reaction WHERE country = ?0 AND id = ?1")
    void deleteByCountryAndId(String country, Long id);

    @Query("SELECT COUNT(*) FROM tbl_reaction WHERE country = ?0 AND id = ?1")
    long countByCountryAndId(String country, Long id);

    default boolean existsByCountryAndId(String country, Long id) {
        return countByCountryAndId(country, id) > 0;
    }
}
