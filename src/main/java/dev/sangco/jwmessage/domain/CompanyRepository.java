package dev.sangco.jwmessage.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, String>, QuerydslPredicateExecutor<Company> {
    Optional<Company> findByCompanyName(String companyName);
}
