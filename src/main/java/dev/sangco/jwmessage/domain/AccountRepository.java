package dev.sangco.jwmessage.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByAccountId(String accountId);
    // TODO 자바8 Optional 적용해보기
}
