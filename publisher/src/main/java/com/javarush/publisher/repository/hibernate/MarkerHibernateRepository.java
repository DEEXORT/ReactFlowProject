package com.javarush.reactflow.repository.hibernate;

import com.javarush.reactflow.model.marker.Marker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarkerHibernateRepository extends JpaRepository<Marker, Long> {
    boolean existsByName(String name);
}
