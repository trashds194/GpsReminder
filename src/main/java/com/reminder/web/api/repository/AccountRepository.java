package com.reminder.web.api.repository;

import com.reminder.web.api.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findOneByUsername(String username);
}
