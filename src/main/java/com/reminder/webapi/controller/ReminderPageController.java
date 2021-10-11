package com.reminder.webapi.controller;

import com.reminder.webapi.model.Reminder;
import com.reminder.webapi.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ReminderPageController {
    private final ReminderService reminderService;

    @Autowired
    public ReminderPageController(ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    @GetMapping(value = "/reminders")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String read(Model model) {
        final List<Reminder> reminders = reminderService.readAll();

        model.addAttribute("reminders", reminders);
        return "reminders";
    }

    @GetMapping(value = "/reminders/{login}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public String readAllUserLocations(@PathVariable(name = "login") String login, @AuthenticationPrincipal UserDetails userDetails, Model model) {
        final List<Reminder> reminderList = reminderService.readAllUserLocations(login, userDetails);


        model.addAttribute("reminders", reminderList);
        return "reminders";
    }
}
