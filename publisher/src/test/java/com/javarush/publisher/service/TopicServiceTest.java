package com.javarush.reactflow.service;

import com.javarush.reactflow.mapper.TopicDto;
import com.javarush.reactflow.model.marker.Marker;
import com.javarush.reactflow.model.topic.Topic;
import com.javarush.reactflow.model.topic.TopicRequestTo;
import com.javarush.reactflow.model.topic.TopicResponseTo;
import com.javarush.reactflow.model.writer.Writer;
import com.javarush.reactflow.repository.hibernate.TopicHibernateRepository;
import com.javarush.reactflow.repository.hibernate.WriterHibernateRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Slf4j
class TopicServiceTest {
    @Mock
    private TopicHibernateRepository topicRepository;

    @Mock
    private WriterHibernateRepository writerRepository;

    @Mock
    private TopicDto mapper;

    @InjectMocks
    private TopicService topicService;

    private Topic topic;
    private TopicRequestTo topicRequestTo;
    private TopicResponseTo topicResponseTo;
    private Writer writer;

    @BeforeEach
    void setUp() {
        writer = Writer.builder()
                .id(1L)
                .login("admin")
                .password("admin")
                .firstname("Jack")
                .lastname("Smith")
                .build();
        Marker marker = Marker.builder().name("marker").build();
        topic = Topic.builder()
                .title("title")
                .content("content")
                .writer(writer)
                .markers(new ArrayList<>(List.of(marker)))
                .build();
        topicResponseTo = TopicResponseTo.builder()
                .id(1L)
                .title("title")
                .content("content")
                .writerId(writer.getId())
                .markers(new String[]{marker.getName()})
                .build();
        topicRequestTo = TopicRequestTo.builder()
                .title("title")
                .content("content")
                .writerId(writer.getId())
                .markers(new ArrayList<>(List.of(marker.getName())))
                .build();
    }

    @Test
    void getById() {
        when(topicRepository.findById(1L)).thenReturn(Optional.of(topic));
        when(mapper.toDto(topic)).thenReturn(topicResponseTo);

        TopicResponseTo actualTo = topicService.getById(1L);

        assertNotNull(actualTo);
    }

    @Test
    void getAll() {
        when(topicRepository.findAll()).thenReturn(List.of(topic));
        when(mapper.toDto(topic)).thenReturn(topicResponseTo);

        List<TopicResponseTo> actualList = topicService.getAll();

        assertNotNull(actualList);
    }

    @Test
    void create() {
        when(mapper.toDto(topic)).thenReturn(topicResponseTo);
        when(topicRepository.save(topic)).thenReturn(topic);
        when(writerRepository.findById(writer.getId())).thenReturn(Optional.of(writer));
        when(mapper.fromDto(topicRequestTo)).thenReturn(topic);

        TopicResponseTo responseTo = topicService.create(topicRequestTo);

        assertNotNull(responseTo);
    }

    @Test
    void update() {
        when(topicRepository.save(topic)).thenReturn(topic);
        when(mapper.toDto(topic)).thenReturn(topicResponseTo);
        when(mapper.fromDto(topicRequestTo)).thenReturn(topic);
        topic.setContent("content2");
        topicRequestTo.setContent("content2");
        topicResponseTo.setContent("content2");

        TopicResponseTo updated = topicService.update(topicRequestTo);

        assertNotNull(updated);
        assertEquals("content2", updated.getContent());
    }

    @Test
    void delete() {
    }
}