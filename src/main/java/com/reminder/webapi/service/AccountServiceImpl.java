package com.reminder.webapi.service;

import com.reminder.webapi.model.Account;
import com.reminder.webapi.reference.AccountRole;
import com.reminder.webapi.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
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
        if (accountRepository.findOneByUsername("Ann") == null) {
            create(new Account("Ann", "mam1404", "mam1404@yandex.ru", AccountRole.USER.name()));
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
    public List<Account> readAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account read(long id) {
        return accountRepository.getById(id);
    }

    @Override
    public boolean update(Account account, long id) {
        if (accountRepository.existsById(id)) {
            account.setId(id);
            accountRepository.save(account);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        if (accountRepository.existsById(id)) {
            accountRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
