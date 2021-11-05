package com.reminder.web.api.service;

import com.reminder.web.api.model.Account;

import java.util.List;

public interface AccountService {

    Account create(Account account);

    List<Account> readAll();

    Account read(long id);

    boolean update(Account account, long id);

    boolean delete(long id);

    Account findByLogin(String login);

    Account findByLoginAndPassword(String login, String password);
}
