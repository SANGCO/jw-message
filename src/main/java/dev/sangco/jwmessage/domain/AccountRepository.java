package dev.sangco.jwmessage.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String>, QuerydslPredicateExecutor<Account> {
    Optional<Account> findByAccId(String accId);

    void deleteByAccId(String accId);
}
