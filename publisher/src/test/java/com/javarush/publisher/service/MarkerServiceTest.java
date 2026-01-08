package com.javarush.reactflow.service;

import com.javarush.reactflow.mapper.MarkerDto;
import com.javarush.reactflow.model.marker.Marker;
import com.javarush.reactflow.model.marker.MarkerRequestTo;
import com.javarush.reactflow.model.marker.MarkerResponseTo;
import com.javarush.reactflow.repository.hibernate.MarkerHibernateRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MarkerServiceTest {

    @Mock
    MarkerHibernateRepository repository;

    @Mock
    MarkerDto mapper;

    @InjectMocks
    MarkerService markerService;

    private Marker mockEntity;
    private MarkerRequestTo mockIn;
    private MarkerResponseTo mockOut;

    @BeforeEach
    void setUp() {
        mockIn = MarkerRequestTo.builder()
                .id(1L)
                .name("test")
                .build();
        mockEntity = Marker.builder().id(1L).name("test").build();
        mockOut = MarkerResponseTo.builder().id(1L).name("test").build();
    }

    @Test
    void create() {
        MarkerRequestTo markerRequestTo = MarkerRequestTo.builder()
                .name("test")
                .build();
        Marker marker = Marker.builder().name("test").build();
        MarkerResponseTo markerResponseTo = MarkerResponseTo.builder().name("test").build();
        when(repository.save(marker)).thenReturn(marker);
        when(mapper.toDto(marker)).thenReturn(markerResponseTo);
        when(mapper.fromDto(markerRequestTo)).thenReturn(marker);

        MarkerResponseTo actual = markerService.create(markerRequestTo);

        assertNotNull(actual);
        assertEquals(markerResponseTo, actual);

        verify(repository, times(1)).save(marker);
        verify(mapper, times(1)).toDto(marker);
        verify(mapper, times(1)).fromDto(markerRequestTo);
    }

    @Test
    void getById() {
        when(repository.findById(mockEntity.getId())).thenReturn(Optional.of(mockEntity));
        when(mapper.toDto(mockEntity)).thenReturn(mockOut);

        MarkerResponseTo actual = markerService.getById(mockEntity.getId());

        assertNotNull(actual);
        assertEquals(mockOut, actual);

        verify(repository, times(1)).findById(mockEntity.getId());
        verify(mapper, times(1)).toDto(mockEntity);
    }

    @Test
    void getAll() {
        when(repository.findAll()).thenReturn(Collections.singletonList(mockEntity));
        when(mapper.toDto(mockEntity)).thenReturn(mockOut);

        Collection<MarkerResponseTo> actual = markerService.getAll();

        assertNotNull(actual);
        assertEquals(1, actual.size());

        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).toDto(mockEntity);
    }

    @Test
    void update() {
        when(repository.save(mockEntity)).thenReturn(mockEntity);
        when(mapper.toDto(mockEntity)).thenReturn(mockOut);
        when(mapper.fromDto(mockIn)).thenReturn(mockEntity);

        MarkerResponseTo actual = markerService.update(mockIn);

        assertNotNull(actual);
        assertEquals(mockOut, actual);
        verify(repository, times(1)).save(mockEntity);
        verify(mapper, times(1)).toDto(mockEntity);
        verify(mapper, times(1)).fromDto(mockIn);
    }

    @Test
    void deleteById() {
        when(repository.existsById(mockEntity.getId())).thenReturn(true);

        boolean actual = markerService.deleteById(mockEntity.getId());

        assertTrue(actual);
        verify(repository, times(1)).deleteById(mockEntity.getId());
    }
}