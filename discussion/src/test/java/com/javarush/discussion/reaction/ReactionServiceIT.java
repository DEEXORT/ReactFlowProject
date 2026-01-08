package com.javarush.discussion.reaction;

import com.javarush.discussion.reaction.model.ReactionRequestTo;
import com.javarush.discussion.reaction.model.ReactionResponseTo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestConstructor;
import org.testcontainers.cassandra.CassandraContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Testcontainers(disabledWithoutDocker = true)
@ActiveProfiles("test")
@Slf4j
class ReactionServiceIT {
    private final ReactionService reactionService;
    private static final String KEYSPACE = "test_keyspace";
    private static final String LOCAL_DATA_CENTER = "datacenter1";

    @Container
    private static final CassandraContainer cassandraContainer = new CassandraContainer("cassandra:4.0.11")
            .withExposedPorts(9042)
            .withInitScript("init.cql"); // For creating keyspace in test container

    @DynamicPropertySource
    static void cassandraProperties(DynamicPropertyRegistry registry) {

        // Connection settings for Cassandra
        registry.add("spring.cassandra.contact-points",
                () -> cassandraContainer.getHost() + ":" + cassandraContainer.getMappedPort(9042));
        registry.add("spring.cassandra.port", () -> cassandraContainer.getMappedPort(9042));
        registry.add("spring.cassandra.local-datacenter",
                () -> LOCAL_DATA_CENTER);
        registry.add("spring.cassandra.keyspace-name",
                () -> KEYSPACE);

        // Connection settings for Liquibase
        registry.add("spring.liquibase.url", () -> "jdbc:cassandra://%s:%d/%s?compliancemode=Liquibase&localdatacenter=%s"
                .formatted(
                        cassandraContainer.getHost(),
                        cassandraContainer.getMappedPort(9042),
                        KEYSPACE,
                        LOCAL_DATA_CENTER));
    }

    @Test
    void getAll() {
        ReactionRequestTo requestTo1 = ReactionRequestTo.builder()
                .content("This is a test1")
                .topicId(1L)
                .build();
        reactionService.create(requestTo1, Locale.of("ru"));
        ReactionRequestTo requestTo2 = ReactionRequestTo.builder()
                .content("This is a test2")
                .topicId(2L)
                .build();
        reactionService.create(requestTo2, Locale.of("ru"));

        List<ReactionResponseTo> reactions = reactionService.getAll();
        assertNotEquals(0, reactions.size());
    }

    @Test
    void getById() {
        ReactionRequestTo requestTo = ReactionRequestTo.builder()
                .content("This is a test")
                .topicId(1L)
                .build();
        ReactionResponseTo created = reactionService.create(requestTo, Locale.of("ru"));

        ReactionResponseTo response = reactionService.getById(created.getId(), Locale.of("ru"));
        assertNotNull(response);
        assertEquals(created.getId(), response.getId());
    }

    @Test
    void create() {
        System.out.println("Cassandra host: " + cassandraContainer.getHost());
        System.out.println("Cassandra port: " + cassandraContainer.getMappedPort(9042));
        ReactionRequestTo requestTo = ReactionRequestTo.builder()
                .content("This is a test")
                .topicId(1L)
                .build();

        ReactionResponseTo responseTo = reactionService.create(requestTo, Locale.of("ru"));
        assertNotNull(responseTo);
        assertEquals(responseTo.getContent(), requestTo.getContent());
        assertEquals(responseTo.getTopicId(), requestTo.getTopicId());
    }

    @Test
    void update() {
        ReactionRequestTo requestTo = ReactionRequestTo.builder()
                .content("This is a test")
                .topicId(1L)
                .build();
        ReactionResponseTo responseTo = reactionService.create(requestTo, Locale.of("ru"));
        ReactionRequestTo updatedContent = ReactionRequestTo.builder()
                .id(responseTo.getId())
                .content("Updated content")
                .topicId(responseTo.getTopicId())
                .build();

        ReactionResponseTo updated = reactionService.update(updatedContent, Locale.of("ru"));
        assertNotNull(updated);
        assertEquals(updated.getContent(), updatedContent.getContent());
        assertEquals(updated.getTopicId(), updatedContent.getTopicId());
        assertEquals(updated.getId(), updatedContent.getId());
    }

    @Test
    void deleteById() {
        ReactionRequestTo requestTo = ReactionRequestTo.builder()
                .content("This is a test")
                .topicId(1L)
                .build();
        ReactionResponseTo responseTo = reactionService.create(requestTo, Locale.of("ru"));

        boolean deleted = reactionService.deleteById(responseTo.getId(), Locale.of("ru"));
        assertTrue(deleted);
    }

}