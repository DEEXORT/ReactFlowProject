package com.javarush.publisher.controller.v2;

import com.javarush.publisher.model.marker.MarkerRequestTo;
import com.javarush.publisher.model.marker.MarkerResponseTo;
import com.javarush.publisher.service.MarkerService;
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
@RequestMapping("/api/v2.0/markers")
@AllArgsConstructor
public class MarkerControllerV2 {
    private final MarkerService markerService;

    @GetMapping
    public Collection<MarkerResponseTo> getMarkers() {
        return markerService.getAll();
    }

    @GetMapping("/{id}")
    public MarkerResponseTo getMarker(@PathVariable Long id) {
        return markerService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MarkerResponseTo createMarker(@Valid @RequestBody MarkerRequestTo markerRequestTo) {
        return markerService.create(markerRequestTo);
    }

    @PutMapping
    public MarkerResponseTo updateMarker(@Valid @RequestBody MarkerRequestTo markerRequestTo) {
        return markerService.update(markerRequestTo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMarker(@PathVariable Long id) {
        boolean success = markerService.deleteById(id);
        if (!success) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
