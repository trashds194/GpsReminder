package com.reminder.webapi.repository;

import com.reminder.webapi.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findOneByUsername(String username);
}
