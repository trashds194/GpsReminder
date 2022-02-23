package com.reminder.web;

import com.reminder.web.api.controller.AccountController;
import com.reminder.web.api.controller.ReminderController;
import com.reminder.web.api.repository.AccountRepository;
import com.reminder.web.api.repository.ReminderRepository;
import com.reminder.web.api.service.AccountService;
import com.reminder.web.api.service.ReminderService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
class WebApplicationTests {

    @MockBean
    private AccountController accountController;

    @MockBean
    private ReminderController reminderController;

    @MockBean
    private AccountService accountService;

    @MockBean
    private ReminderService reminderService;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private ReminderRepository reminderRepository;

    @Test
    void contextLoads() {
        assertThat(accountRepository).isNotNull();
        assertThat(reminderRepository).isNotNull();
        assertThat(accountService).isNotNull();
        assertThat(reminderService).isNotNull();
        assertThat(accountController).isNotNull();
        assertThat(reminderController).isNotNull();
    }

}
