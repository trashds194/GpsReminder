package com.reminder.web.api.service;

import com.reminder.web.api.model.Reminder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface ReminderService {
    boolean create(Reminder reminder, UserDetails userDetails);

    List<Reminder> readAll(UserDetails userDetails);

    List<Reminder> readAllUserLocations(UserDetails userDetails);

    Reminder read(long id, UserDetails userDetails);

    Reminder search(String Title, UserDetails userDetails);

    boolean update(Reminder reminder, long id, UserDetails userDetails);

    boolean delete(long id, UserDetails userDetails);
}
