package com.reminder.web.api.service;

import com.reminder.web.api.model.Account;
import com.reminder.web.api.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class AuthenticationService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Autowired
    public AuthenticationService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findOneByUsername(username);
        System.out.println(account.toString());
        if (account == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        return createUser(account);
    }

    private User createUser(Account account) {
        return new User(account.getUsername(), account.getPassword(), createAuthorities(account));
    }

    private Collection<GrantedAuthority> createAuthorities(Account account) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + account.getRole()));
        return authorities;
    }
}
