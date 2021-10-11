package com.reminder.webapi.controller;

import com.reminder.webapi.model.Reminder;
import com.reminder.webapi.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReminderController {
    private final ReminderService reminderService;

    @Autowired
    public ReminderController(ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    @PostMapping(value = "api/reminders/{login}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<?> create(@RequestBody Reminder reminder, @AuthenticationPrincipal UserDetails userDetails) {
        final boolean created = reminderService.create(reminder, userDetails);

        return created
                ? new ResponseEntity<>(HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
    }

    @GetMapping(value = "api/reminders")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<Reminder>> read() {
        final List<Reminder> reminders = reminderService.readAll();

        return reminders != null && !reminders.isEmpty()
                ? new ResponseEntity<>(reminders, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "api/reminders/{login}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<List<Reminder>> readAllUserLocations(@PathVariable(name = "login") String login, @AuthenticationPrincipal UserDetails userDetails) {
        final List<Reminder> reminderList = reminderService.readAllUserLocations(login, userDetails);

        return reminderList != null && !reminderList.isEmpty()
                ? new ResponseEntity<>(reminderList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "api/reminders/{login}/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Reminder> read(@PathVariable(name = "login") String login, @PathVariable(name = "id") long id, @AuthenticationPrincipal UserDetails userDetails) {
        final Reminder reminder = reminderService.read(login, id, userDetails);

        return reminder != null
                ? new ResponseEntity<>(reminder, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "api/reminders/{login}/search")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Reminder> search(@PathVariable(name = "login") String login, @RequestParam(required = false) String title, @AuthenticationPrincipal UserDetails userDetails) {
        final Reminder reminder = reminderService.search(login, title, userDetails);

        return reminder != null
                ? new ResponseEntity<>(reminder, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "api/reminders/{login}/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> update(@PathVariable(name = "login") String login, @PathVariable(name = "id") long id, @RequestBody Reminder reminder, @AuthenticationPrincipal UserDetails userDetails) {
        final boolean updated = reminderService.update(login, reminder, id, userDetails);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "api/reminders/{login}/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<?> delete(@PathVariable(name = "login") String login, @PathVariable(name = "id") long id, @AuthenticationPrincipal UserDetails userDetails) {
        final boolean deleted = reminderService.delete(login, id, userDetails);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
