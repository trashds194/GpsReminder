package com.reminder.webapi.service;

import com.reminder.webapi.model.Account;
import com.reminder.webapi.repository.AccountRepository;
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
public class AuthenticationService implements UserDetailsService{

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findOneByUsername(username);
        System.out.println(account.toString());
        if (account == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé : " + username);
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
