package com.javarush.publisher.repository.hibernate;

import com.javarush.publisher.model.marker.Marker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarkerHibernateRepository extends JpaRepository<Marker, Long> {
    boolean existsByName(String name);
}
