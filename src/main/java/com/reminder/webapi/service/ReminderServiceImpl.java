package com.reminder.webapi.service;

import com.reminder.webapi.model.Account;
import com.reminder.webapi.model.Reminder;
import com.reminder.webapi.repository.AccountRepository;
import com.reminder.webapi.repository.ReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class ReminderServiceImpl implements ReminderService {

    private final ReminderRepository reminderRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public ReminderServiceImpl(ReminderRepository reminderRepository, AccountRepository accountRepository) {
        this.reminderRepository = reminderRepository;
        this.accountRepository = accountRepository;
    }


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
    public List<Reminder> readAll() {
        return reminderRepository.findAll();
    }

    @Override
    public List<Reminder> readAllUserLocations(String login, UserDetails userDetails) {
        System.out.println(userDetails.getUsername());
        Account account = accountRepository.findOneByUsername(userDetails.getUsername());
        if (account.getUsername().equals(login))
            return reminderRepository.findAllByUserId(account.getId());
        else return Collections.emptyList();
    }

//    @Override
//    public Reminder read(long id) {
//        return reminderRepository.getById(id);
//    }

    @Override
    public Reminder read(String login, long id, UserDetails userDetails) {
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
    public Reminder search(String login, String title, UserDetails userDetails) {
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
    public boolean update(String login, Reminder reminder, long id, UserDetails userDetails) {
        Account account = accountRepository.findOneByUsername(userDetails.getUsername());
        if (Objects.equals(account.getId(), reminder.getUserId())) {
            if (reminderRepository.existsById(id)) {
                reminder.setId(id);
                reminderRepository.save(reminder);
                return true;
            }
        } else return false;
        return false;
    }

    @Override
    public boolean delete(String login, long id, UserDetails userDetails) {
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
