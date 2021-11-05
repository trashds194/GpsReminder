package com.reminder.web.api.service;

import com.reminder.web.api.model.Account;
import com.reminder.web.api.model.Reminder;
import com.reminder.web.api.repository.AccountRepository;
import com.reminder.web.api.repository.ReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class ReminderServiceImpl implements ReminderService {

    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public boolean create(Reminder reminder, UserDetails userDetails) {
        if (userDetails.getUsername() != null) {
            Account account = accountRepository.findOneByUsername(userDetails.getUsername());
            reminder.setUserId(account.getId());
            reminder.setDate(LocalDate.now());
            reminderRepository.save(reminder);
            return true;
        } else return false;
    }

    @Override
    public List<Reminder> readAll(UserDetails userDetails) {
        Account account = accountRepository.findOneByUsername(userDetails.getUsername());
        if (account.getRole().equals("ADMIN")) {
            return reminderRepository.findAll();
        } else return null;
    }

    @Override
    public List<Reminder> readAllUserLocations(UserDetails userDetails) {
        System.out.println(userDetails.getUsername());
        Account account = accountRepository.findOneByUsername(userDetails.getUsername());
        if (account.getUsername().equals(userDetails.getUsername()))
            return reminderRepository.findAllByUserId(account.getId());
        else return Collections.emptyList();
    }

    @Override
    public Reminder read(long id, UserDetails userDetails) {
        try {
            Account account = accountRepository.findOneByUsername(userDetails.getUsername());
            Reminder reminder = reminderRepository.getById(id);
            if (Objects.equals(account.getId(), reminder.getUserId())) {
                return reminder;
            } else return null;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return null;
        }
    }

    @Override
    public Reminder search(String title, UserDetails userDetails) {
        try {
            Account account = accountRepository.findOneByUsername(userDetails.getUsername());
            Reminder reminder = reminderRepository.findByTitle(title);
            if (Objects.equals(account.getId(), reminder.getUserId())) {
                return reminder;
            } else return null;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return null;
        }
    }

    @Override
    public boolean update(Reminder reminder, long id, UserDetails userDetails) {
        Account account = accountRepository.findOneByUsername(userDetails.getUsername());
        Reminder findReminder = reminderRepository.getById(id);
        if (Objects.equals(account.getId(), findReminder.getUserId())) {
            if (reminderRepository.existsById(id)) {
                reminder.setId(id);
                reminder.setUserId(findReminder.getUserId());
                reminderRepository.save(reminder);
                return true;
            }
        } else return false;
        return false;
    }

    @Override
    public boolean delete(long id, UserDetails userDetails) {
        Account account = accountRepository.findOneByUsername(userDetails.getUsername());
        if (Objects.equals(account.getId(), reminderRepository.findById(id).get().getUserId())) {
            if (reminderRepository.existsById(id)) {
                reminderRepository.deleteById(id);
                return true;
            }
        }
        return false;
    }
}
