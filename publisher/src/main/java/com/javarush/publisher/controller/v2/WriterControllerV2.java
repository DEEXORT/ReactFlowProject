package com.javarush.publisher.controller;

import com.javarush.publisher.model.writer.WriterRequestTo;
import com.javarush.publisher.model.writer.WriterResponseTo;
import com.javarush.publisher.service.WriterService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/api/v2.0/writers")
@AllArgsConstructor
@Slf4j
public class WriterControllerV2 {
    private WriterService writerService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Collection<WriterResponseTo> getWriters() {
        return writerService.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER') and " +
            "(@securityService.isOwner(#id, authentication.name) or hasRole('ADMIN'))")
    public WriterResponseTo getWriterById(@PathVariable Long id) {
        return writerService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WriterResponseTo createWriter(@Valid @RequestBody WriterRequestTo writerRequestTo) {
        log.info("Creating writer {}", writerRequestTo.getLogin());
        return writerService.create(writerRequestTo);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER') and " +
            "(@securityService.isOwner(#writerRequestTo.id, authentication.name) or hasRole('ADMIN'))")
    public WriterResponseTo updateWriter(@Valid @RequestBody WriterRequestTo writerRequestTo) {
        return writerService.update(writerRequestTo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER') and " +
            "(@securityService.isOwner(#id, authentication.name) or hasRole('ADMIN'))")
    public void deleteWriterById(@PathVariable Long id) {
        boolean isDeleted = writerService.deleteById(id);
        if (!isDeleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
