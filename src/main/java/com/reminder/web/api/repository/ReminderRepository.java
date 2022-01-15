package com.reminder.web.api.repository;

import com.reminder.web.api.model.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    List<Reminder> findAllByUserId(long id);

    Reminder findByTitle(String Title);
}
