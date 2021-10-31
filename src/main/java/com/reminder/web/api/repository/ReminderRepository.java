package com.reminder.web.api.repository;

import com.reminder.web.api.model.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    List<Reminder> findAllByUserId(long id);

    Reminder findByTitle(String Title);
}
