package com.javarush.publisher.controller;

import com.javarush.publisher.model.topic.TopicRequestTo;
import com.javarush.publisher.model.topic.TopicResponseTo;
import com.javarush.publisher.service.TopicService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1.0/topics")
@AllArgsConstructor
public class TopicController {
    private final TopicService topicService;

    @GetMapping
    public Collection<TopicResponseTo> getTopics() {
        return topicService.getAll();
    }

    @GetMapping("/{id}")
    public TopicResponseTo getTopicById(@PathVariable Long id) {
        return topicService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TopicResponseTo createTopic(@Valid @RequestBody TopicRequestTo topic) {
        return topicService.create(topic);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public TopicResponseTo updateTopic(@Valid @RequestBody TopicRequestTo topic) {
        return topicService.update(topic);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTopic(@PathVariable Long id) {
        boolean success = topicService.delete(id);
        if (!success) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
