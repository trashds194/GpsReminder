package com.reminder.webapi.repository;

import com.reminder.webapi.model.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    List<Reminder> findAllByUserId(long id);

    Reminder findByTitle(String Title);
}
