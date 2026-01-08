package com.javarush.reactflow.service;

import com.javarush.reactflow.model.reaction.ReactionRequestTo;
import com.javarush.reactflow.model.reaction.ReactionResponseTo;
import com.javarush.reactflow.model.topic.TopicRequestTo;
import com.javarush.reactflow.model.topic.TopicResponseTo;
import com.javarush.reactflow.model.writer.WriterRequestTo;
import com.javarush.reactflow.model.writer.WriterResponseTo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
@Transactional
@Slf4j
class ReactionRestServiceIT {
    private final WriterService writerService;
    private final TopicService topicService;
    private final ReactionRestService reactionRestService;

    @Test
    void getAll() {
        WriterRequestTo writer = getWriter();
        WriterResponseTo writerResponseTo = writerService.create(writer);
        TopicRequestTo topicRequestTo = getTopic(writerResponseTo);
        TopicResponseTo responseTo = topicService.create(topicRequestTo);
        ReactionRequestTo requestTo = getReaction(responseTo);
        reactionRestService.create(requestTo);
        log.info(requestTo.toString());

        // when
        List<ReactionResponseTo> reactions = reactionRestService.getAll();
        log.info(reactions.toString());
        assertNotEquals(0, reactions.size());
    }

    private static ReactionRequestTo getReaction(TopicResponseTo responseTo) {
        return ReactionRequestTo.builder()
                .content("test")
                .topicId(responseTo.getId())
                .build();
    }

    private static TopicRequestTo getTopic(WriterResponseTo writerResponseTo) {
        ArrayList<String> markers = new ArrayList<>();
        markers.add("Marker 1");
        markers.add("Marker 2");
        return TopicRequestTo.builder()
                .title("title")
                .writerId(writerResponseTo.getId())
                .content("content")
                .markers(markers)
                .build();
    }

    private static WriterRequestTo getWriter() {
        return WriterRequestTo.builder()
                .login("admin")
                .password("admin")
                .firstname("admin")
                .lastname("admin")
                .build();
    }

    @Test
    void getById() {
        // given
        WriterRequestTo writer = getWriter();
        WriterResponseTo writerResponseTo = writerService.create(writer);
        TopicRequestTo topicRequestTo = getTopic(writerResponseTo);
        TopicResponseTo responseTo = topicService.create(topicRequestTo);
        ReactionRequestTo requestTo = getReaction(responseTo);
        ReactionResponseTo reactionResponseTo = reactionRestService.create(requestTo);
        log.info(reactionResponseTo.toString());

        // when
        ReactionResponseTo response = reactionRestService.getById(reactionResponseTo.getId());
        log.info(response.toString());
        assertNotEquals(0, response.getId());
        assertNotNull(response);
    }

    @Test
    void create() {
        // given
        WriterRequestTo writer = getWriter();
        WriterResponseTo writerResponseTo = writerService.create(writer);
        TopicRequestTo topicRequestTo = getTopic(writerResponseTo);
        TopicResponseTo responseTo = topicService.create(topicRequestTo);
        ReactionRequestTo requestTo = getReaction(responseTo);

        // when
        ReactionResponseTo response = reactionRestService.create(requestTo);

        assertNotNull(response);
        assertNotNull(response.getId());
    }

    @Test
    void update() {
        // given
        WriterRequestTo writer = getWriter();
        WriterResponseTo writerResponseTo = writerService.create(writer);
        TopicRequestTo topicRequestTo = getTopic(writerResponseTo);
        TopicResponseTo responseTo = topicService.create(topicRequestTo);
        ReactionRequestTo requestTo = getReaction(responseTo);
        ReactionResponseTo reactionResponseTo = reactionRestService.create(requestTo);
        log.info(reactionResponseTo.toString());
        requestTo.setId(reactionResponseTo.getId());
        requestTo.setContent("updated");

        ReactionResponseTo response = reactionRestService.update(requestTo);

        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals("updated", response.getContent());
    }

    @Test
    void deleteById() {
        // given
        WriterRequestTo writer = getWriter();
        WriterResponseTo writerResponseTo = writerService.create(writer);
        TopicRequestTo topicRequestTo = getTopic(writerResponseTo);
        TopicResponseTo responseTo = topicService.create(topicRequestTo);
        ReactionRequestTo requestTo = getReaction(responseTo);
        ReactionResponseTo reactionResponseTo = reactionRestService.create(requestTo);
        log.info(reactionResponseTo.toString());

        boolean deleted = reactionRestService.deleteById(reactionResponseTo.getId());
        assertTrue(deleted);
    }
}