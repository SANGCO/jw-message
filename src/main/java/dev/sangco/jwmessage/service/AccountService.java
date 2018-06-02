package dev.sangco.jwmessage.service;

import dev.sangco.jwmessage.common.AccountDuplicatedException;
import dev.sangco.jwmessage.common.AccountNotFoundException;
import dev.sangco.jwmessage.domain.Account;
import dev.sangco.jwmessage.domain.AccountDto;
import dev.sangco.jwmessage.domain.AccountRepository;
import dev.sangco.jwmessage.domain.Role;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Transactional
@Service
public class AccountService {
    public static final Logger log = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Account createAccount(AccountDto.Create accDtoCreate) {
        Account account = modelMapper.map(accDtoCreate, Account.class);
        String accountId = accDtoCreate.getAccountId();
        accountRepository.findByAccountId(accountId).ifPresent(s -> {
            throw new AccountDuplicatedException(accountId);
        });
        account.setRole(Role.USER);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    public Account findByAccountId(String accountId) {
        return accountRepository.findByAccountId(accountId).orElseThrow(() -> new AccountNotFoundException(accountId));
    }

    public void deleteAll() {
        accountRepository.deleteAll();
    }
}
