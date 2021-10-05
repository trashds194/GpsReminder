package com.reminder.webapi.service;

import com.reminder.webapi.model.Account;

import java.util.List;

public interface AccountService {
    Account create(Account account);

    List<Account> readAll();

    Account read(long id);

    boolean update(Account account, long id);

    boolean delete(long id);
}
