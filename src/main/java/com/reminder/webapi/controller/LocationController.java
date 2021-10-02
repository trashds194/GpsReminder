package com.reminder.webapi.controller;

import com.reminder.webapi.model.Location;
import com.reminder.webapi.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
public class LocationController {
    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping(value = "/locations")
    public ResponseEntity<?> create(@RequestBody Location location) {
        locationService.create(location);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/locations")
    public ResponseEntity<List<Location>> read() {
        final List<Location> locations = locationService.readAll();

        return locations != null && !locations.isEmpty()
                ? new ResponseEntity<>(locations, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/locations/{id}")
    public ResponseEntity<Location> read(@PathVariable(name = "id") int id) {
        final Location location = locationService.read(id);

        return location != null
                ? new ResponseEntity<>(location, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/locations/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id, @RequestBody Location location) {
        final boolean updated = locationService.update(location, id);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/locations/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        final boolean deleted = locationService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
