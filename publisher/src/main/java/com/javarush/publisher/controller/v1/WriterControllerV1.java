package com.javarush.publisher.controller.v1;

import com.javarush.publisher.model.writer.WriterRequestTo;
import com.javarush.publisher.model.writer.WriterResponseTo;
import com.javarush.publisher.service.WriterService;
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
@RequestMapping("/api/v1.0/writers")
@AllArgsConstructor
public class WriterControllerV1 {
    private WriterService writerService;

    @GetMapping
    public Collection<WriterResponseTo> getWriters() {
        return writerService.getAll();
    }

    @GetMapping("/{id}")
    public WriterResponseTo getWriterById(@PathVariable Long id) {
        return writerService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WriterResponseTo createWriter(@Valid @RequestBody WriterRequestTo writerRequestTo) {
        return writerService.create(writerRequestTo);
    }

    @PutMapping
    public WriterResponseTo updateWriter(@Valid @RequestBody WriterRequestTo writerRequestTo) {
        return writerService.update(writerRequestTo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWriterById(@PathVariable Long id) {
        boolean isDeleted = writerService.deleteById(id);
        if (!isDeleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
