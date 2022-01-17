package com.reminder.web.api.service.impl;

import com.reminder.web.WebApplication;
import com.reminder.web.api.model.Account;
import com.reminder.web.api.repository.AccountRepository;
import com.reminder.web.api.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
public class AccountServiceImplTest {

    @MockBean
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void create() {
        Account test = new Account("test", "test");
        when(accountRepository.save(test)).thenReturn(test);
        assertTrue(new ReflectionEquals(test).matches(accountService.create(test)));
    }

    @Test
    public void readAll() {
        given(accountRepository.findAll()).willReturn(Collections.emptyList());
        assertEquals(new ArrayList<>(), accountService.readAll());
    }

    @Test
    public void read() {
        given(accountRepository.getById(anyLong())).willReturn(new Account());
        assertTrue(new ReflectionEquals(new Account()).matches(accountService.read(anyLong())));
    }

    @Test
    public void update() {
        Account test = new Account();
        given(accountRepository.existsById(anyLong())).willReturn(true);
        accountService.update(test, anyLong());
        assertTrue(accountRepository.existsById(anyLong()));
        verify(accountRepository).save(test);
    }

    @Test
    public void delete() {
        given(accountRepository.existsById(anyLong())).willReturn(true);
        accountService.delete(anyLong());
        assertTrue(accountRepository.existsById(anyLong()));
        verify(accountRepository).deleteById(anyLong());
    }

    @Test
    public void findByLogin() {
        given(accountRepository.findOneByUsername(anyString())).willReturn(new Account());
        assertTrue(new ReflectionEquals(new Account()).matches(accountService.findByLogin(anyString())));
    }

    @Test
    public void findByLoginAndPassword() {
        Account test = new Account("test", passwordEncoder.encode("test"));
        given(accountService.findByLoginAndPassword("test", "test")).willReturn (test);
        assertTrue(new ReflectionEquals(test).matches(accountService.findByLoginAndPassword("test", "test")));
    }
}