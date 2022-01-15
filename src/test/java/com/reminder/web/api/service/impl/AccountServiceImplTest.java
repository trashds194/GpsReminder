package com.reminder.web.api.service.impl;

import com.reminder.web.api.model.Account;
import com.reminder.web.api.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTest {

    @Mock
    private AccountService accountService;

    @Test
    public void create() {
    }

    @Test
    public void readAll() {
        given(accountService.readAll()).willReturn(Collections.EMPTY_LIST);
        assertEquals(new ArrayList<>(), accountService.readAll());
    }

    @Test
    public void read() {
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
        given(accountService.delete(1)).willReturn(true);
        assertTrue(accountService.delete(1));
    }

    @Test
    public void findByLogin() {
        Account test = new Account(1L, "test", "test", "test", "test");
        given(accountService.findByLogin("test")).willReturn(test);
        assertEquals(test, accountService.findByLogin("test"));
    }

    @Test
    public void findByLoginAndPassword() {
    }
}