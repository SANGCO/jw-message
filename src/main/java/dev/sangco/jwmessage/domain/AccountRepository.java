package dev.sangco.jwmessage.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccId(String accId);

    void deleteByAccId(String accId);
}
