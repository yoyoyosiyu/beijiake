package com.beijiake.resourceservertest.repository;

import com.beijiake.data.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
