package com.reminder.web.api.service.impl;

import com.reminder.web.api.model.Account;
import com.reminder.web.api.reference.AccountRole;
import com.reminder.web.api.repository.AccountRepository;
import com.reminder.web.api.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initialize() {
        if (accountRepository.findOneByUsername("admin") == null) {
            create(new Account("admin", "admin", "trashds194@yandex.ru", AccountRole.ADMIN.name()));
        }
        if (accountRepository.findOneByUsername("test") == null) {
            create(new Account("test", "test", "test@test.ru", AccountRole.USER.name()));
        }
    }

    @Override
    @Transactional
    public Account create(Account account) {
        if (account.getRole() == null) {
            account.setRole(AccountRole.USER.name());
        }
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public List<Account> readAll() {
        return accountRepository.findAll();
    }

    @Override
    @Transactional
    public Account read(long id) {
        return accountRepository.getById(id);
    }

    @Override
    @Transactional
    public boolean update(Account account, long id) {
        if (accountRepository.existsById(id)) {
            account.setId(id);
            accountRepository.save(account);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        if (accountRepository.existsById(id)) {
            accountRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Account findByLogin(String login) {
        return accountRepository.findOneByUsername(login);
    }

    @Override
    @Transactional
    public Account findByLoginAndPassword(String login, String password) {
        Account account = findByLogin(login);
        if (account != null) {
            if (passwordEncoder.matches(password, account.getPassword())) {
                return account;
            }
        }
        return null;
    }
}
