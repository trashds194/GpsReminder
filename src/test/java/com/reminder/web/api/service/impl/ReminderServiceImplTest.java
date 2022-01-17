package com.reminder.web.api.service.impl;

import com.reminder.web.WebApplication;
import com.reminder.web.api.repository.ReminderRepository;
import com.reminder.web.api.service.ReminderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
public class ReminderServiceImplTest {

    @MockBean
    private ReminderRepository reminderRepository;

    @Autowired
    private ReminderService reminderService;

    @Test
    public void create() {
    }

    @Test
    public void readAll() {
    }

    @Test
    public void readAllUserLocations() {
    }

    @Test
    public void read() {
    }

    @Test
    public void search() {
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }
}