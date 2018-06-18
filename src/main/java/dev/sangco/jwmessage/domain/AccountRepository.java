package dev.sangco.jwmessage.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
// TODO String으로 변경했는데 체크하기
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccId(String accId);

    void deleteByAccId(String accId);
}
