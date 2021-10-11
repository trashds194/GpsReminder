package com.reminder.webapi.service;

import com.reminder.webapi.model.Reminder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface ReminderService {
    boolean create(Reminder reminder, UserDetails userDetails);

    List<Reminder> readAll();

    List<Reminder> readAllUserLocations(String login, UserDetails userDetails);

//    Reminder read(long id);

    Reminder read(String login, long id, UserDetails userDetails);

    Reminder search(String login, String Title, UserDetails userDetails);

    boolean update(String login, Reminder reminder, long id, UserDetails userDetails);

    boolean delete(String login, long id, UserDetails userDetails);
}
