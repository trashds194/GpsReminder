package com.reminder.webapi.controller;

import com.reminder.webapi.model.Location;
import com.reminder.webapi.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LocationController {
    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping(value = "api/locations/{login}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<?> create(@RequestBody Location location, @AuthenticationPrincipal UserDetails userDetails) {
        final boolean created = locationService.create(location, userDetails);

        return created
                ? new ResponseEntity<>(HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
    }

    @GetMapping(value = "api/locations")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<Location>> read() {
        final List<Location> locations = locationService.readAll();

        return locations != null && !locations.isEmpty()
                ? new ResponseEntity<>(locations, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "api/locations/{login}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<List<Location>> readAllUserLocations(@PathVariable(name = "login") String login, @AuthenticationPrincipal UserDetails userDetails) {
        final List<Location> locationList = locationService.readAllUserLocations(login, userDetails);

        return locationList != null && !locationList.isEmpty()
                ? new ResponseEntity<>(locationList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "api/locations/{login}/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Location> read(@PathVariable(name = "login") String login, @PathVariable(name = "id") long id,@AuthenticationPrincipal UserDetails userDetails) {
        final Location location = locationService.read(login, id, userDetails);

        return location != null
                ? new ResponseEntity<>(location, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "api/locations/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> update(@PathVariable(name = "id") long id, @RequestBody Location location) {
        final boolean updated = locationService.update(location, id);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "api/locations/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<?> delete(@PathVariable(name = "id") long id) {
        final boolean deleted = locationService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
