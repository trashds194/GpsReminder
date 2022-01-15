package com.reminder.web.api.controller;

import com.reminder.web.api.model.Reminder;
import com.reminder.web.api.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class ReminderController {

    @Autowired
    private ReminderService reminderService;

    @PostMapping(value = "/reminders")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<?> create(@RequestBody Reminder reminder, @AuthenticationPrincipal UserDetails userDetails) {
        final boolean created = reminderService.create(reminder, userDetails);

        return created
                ? new ResponseEntity<>(HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/reminders/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<Reminder>> read(@AuthenticationPrincipal UserDetails userDetails) {
        final List<Reminder> reminders = reminderService.readAll(userDetails);

        return reminders != null
                ? new ResponseEntity<>(reminders, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping(value = "/reminders")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<List<Reminder>> readAllUserLocations(@AuthenticationPrincipal UserDetails userDetails) {
        return updateList(userDetails) != null
                ? new ResponseEntity<>(updateList(userDetails), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/reminders/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Reminder> read(@PathVariable(name = "id") long id, @AuthenticationPrincipal UserDetails userDetails) {
        final Reminder reminder = reminderService.read(id, userDetails);

        return reminder != null
                ? new ResponseEntity<>(reminder, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/reminders/search")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Reminder> search(@RequestParam(required = false) String title, @AuthenticationPrincipal UserDetails userDetails) {
        final Reminder reminder = reminderService.search(title, userDetails);

        return reminder != null
                ? new ResponseEntity<>(reminder, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/reminders/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<?> update(@PathVariable(name = "id") long id, @RequestBody Reminder reminder, @AuthenticationPrincipal UserDetails userDetails) {
        final boolean updated = reminderService.update(reminder, id, userDetails);
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/reminders/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<?> delete(@PathVariable(name = "id") long id, @AuthenticationPrincipal UserDetails userDetails) {
        final boolean deleted = reminderService.delete(id, userDetails);
        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    private List<Reminder> updateList(UserDetails userDetails) {
        return reminderService.readAllUserLocations(userDetails);
    }
}
