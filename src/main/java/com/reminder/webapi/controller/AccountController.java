package com.reminder.webapi.controller;

import com.reminder.webapi.config.jwt.JwtProvider;
import com.reminder.webapi.model.Account;
import com.reminder.webapi.service.AccountService;
import com.reminder.webapi.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {
    private final AuthenticationService authenticationService;
    private final AccountService accountService;
    private final JwtProvider jwtProvider;

    @Autowired
    public AccountController(AuthenticationService authenticationService, AccountService accountService, JwtProvider jwtProvider) {
        this.authenticationService = authenticationService;
        this.accountService = accountService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("api/login")
    public ResponseEntity<?> login(@RequestBody Account account) {
        System.out.println(account.getUsername());
        Account logAccount = accountService.findByLoginAndPassword(account.getUsername(), account.getPassword());
        String token = jwtProvider.generateToken(logAccount.getUsername());
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("api/accounts")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<?> create(@RequestBody Account account) {
        accountService.create(account);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "api/accounts")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<Account>> read() {
        final List<Account> accounts = accountService.readAll();

        return accounts != null && !accounts.isEmpty()
                ? new ResponseEntity<>(accounts, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "api/accounts/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Account> read(@PathVariable(name = "id") long id) {
        final Account account = accountService.read(id);

        return account != null
                ? new ResponseEntity<>(account, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "api/accounts/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<?> update(@PathVariable(name = "id") long id, @RequestBody Account account) {
        final boolean updated = accountService.update(account, id);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "api/accounts/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<?> delete(@PathVariable(name = "id") long id) {
        final boolean deleted = accountService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
