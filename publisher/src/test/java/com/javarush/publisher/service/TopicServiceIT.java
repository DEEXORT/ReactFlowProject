package com.javarush.publisher.service;

import com.javarush.publisher.model.topic.TopicRequestTo;
import com.javarush.publisher.model.topic.TopicResponseTo;
import com.javarush.publisher.model.writer.WriterRequestTo;
import com.javarush.publisher.model.writer.WriterResponseTo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
@Transactional
@Slf4j
class TopicServiceIT {
    private final TopicService topicService;
    private final WriterService writerService;
    private final MarkerService markerService;

    @Test
    void getById() {
    }

    @Test
    void getAll() {
    }

    @Test
    void should_CreateWithMarkers() {
        WriterRequestTo writer = WriterRequestTo.builder()
                .login("admin")
                .password("admin")
                .firstname("admin")
                .lastname("admin")
                .build();
        WriterResponseTo writerResponseTo = writerService.create(writer);
        ArrayList<String> markers = new ArrayList<>();
        markers.add("Marker 1");
        markers.add("Marker 2");
        TopicRequestTo topicRequestTo = TopicRequestTo.builder()
                .title("title")
                .writerId(writerResponseTo.getId())
                .content("content")
                .markers(markers)
                .build();

        TopicResponseTo responseTo = topicService.create(topicRequestTo);
        log.info(responseTo.toString());

        markerService.getAll().forEach(System.out::println);
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}