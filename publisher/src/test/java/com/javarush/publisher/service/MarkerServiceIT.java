package com.javarush.publisher.service;

import com.javarush.publisher.exception.MarkerNotFoundException;
import com.javarush.publisher.model.marker.MarkerRequestTo;
import com.javarush.publisher.model.marker.MarkerResponseTo;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
@RequiredArgsConstructor
class MarkerServiceIT {
    private final MarkerService markerService;

    @Test
    void create() {
        MarkerRequestTo markerIn = MarkerRequestTo.builder().name("Marker 1").build();

        MarkerResponseTo response = markerService.create(markerIn);
        assertNotNull(response);
        assertEquals("Marker 1", response.getName());
    }

    @Test
    void getById() {
        MarkerRequestTo markerIn = MarkerRequestTo.builder().name("Marker 1").build();
        MarkerResponseTo response = markerService.create(markerIn);

        MarkerResponseTo actual = markerService.getById(response.getId());
        assertNotNull(actual);
        assertEquals("Marker 1", actual.getName());
    }

    @Test
    void getAll() {
        MarkerRequestTo markerIn = MarkerRequestTo.builder().name("Marker 1").build();
        MarkerResponseTo response = markerService.create(markerIn);

        Collection<MarkerResponseTo> markers = markerService.getAll();
        assertNotNull(markers);
        assertEquals(1, markers.size());
    }

    @Test
    void update() {
        MarkerRequestTo markerIn = MarkerRequestTo.builder().name("Marker 1").build();
        MarkerResponseTo response = markerService.create(markerIn);
        markerIn.setName("Marker 2");

        MarkerResponseTo actual = markerService.update(markerIn);
        assertEquals("Marker 2", actual.getName());
    }

    @Test
    void deleteById() {
        MarkerRequestTo markerIn = MarkerRequestTo.builder().name("Marker 1").build();
        MarkerResponseTo response = markerService.create(markerIn);

        boolean deleted = markerService.deleteById(response.getId());
        assertTrue(deleted);
        assertThrows(MarkerNotFoundException.class, () -> markerService.getById(response.getId()));
    }
}