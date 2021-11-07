package com.reminder.web.api.controller;

import com.reminder.web.api.config.jwt.JwtProvider;
import com.reminder.web.api.model.Account;
import com.reminder.web.api.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.header.Header;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtProvider jwtProvider;


    @PostMapping(value = "/api/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody Account account, HttpServletResponse httpServletResponse) {
        System.out.println(account.getUsername());
        try {
            Account logAccount = accountService.findByLoginAndPassword(account.getUsername(), account.getPassword());
            String token = jwtProvider.generateToken(logAccount.getUsername());

            System.out.println(token);

            HttpHeaders headers = new HttpHeaders();
            addCookie("user_token", headers, httpServletResponse);
            addCookie("username", headers, httpServletResponse);
            headers.add("user_token", token);
            headers.add("username", logAccount.getUsername());

            return new ResponseEntity<>(headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/api/register")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<?> create(@RequestBody Account account) {
        accountService.create(account);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/api/accounts")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<Account>> read() {
        final List<Account> accounts = accountService.readAll();

        return accounts != null && !accounts.isEmpty()
                ? new ResponseEntity<>(accounts, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/api/accounts/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Account> read(@PathVariable(name = "id") long id) {
        final Account account = accountService.read(id);

        return account != null
                ? new ResponseEntity<>(account, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/api/accounts/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<?> update(@PathVariable(name = "id") long id, @RequestBody Account account) {
        final boolean updated = accountService.update(account, id);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/api/accounts/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<?> delete(@PathVariable(name = "id") long id) {
        final boolean deleted = accountService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    private void addCookie(String cookieName, HttpHeaders httpHeaders, HttpServletResponse httpServletResponse) {
        Cookie cookie = new Cookie(cookieName, httpHeaders.getFirst(cookieName));
        cookie.setMaxAge(2 * 24 * 60 * 60); //2 day cookies
        cookie.setSecure(true);
        cookie.setHttpOnly(true);

        httpServletResponse.addCookie(cookie);
    }
}
