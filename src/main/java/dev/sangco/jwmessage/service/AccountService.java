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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

    public Account createAccount(AccountDto.Create createDto) {
        Account account = modelMapper.map(createDto, Account.class);
        String accountId = createDto.getAccountId();
        accountRepository.findByAccountId(accountId).ifPresent(s -> {
            throw new AccountDuplicatedException(accountId);
        });
        account.setRole(Role.USER);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    public Account findById(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(String.valueOf(id)));
    }

    public Account findByAccountId(String accountId) {
        return accountRepository.findByAccountId(accountId).orElseThrow(() -> new AccountNotFoundException(accountId));
    }

    public Page<Account> findAll(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    public Account updateAccount(Long id, AccountDto.Update updateDto) {
        Account account = findById(id);
        account.setPassword(updateDto.getPassword());
        account.setName(updateDto.getName());
        account.setPhoneNumb(updateDto.getPhoneNumb());
        account.setAligoId(updateDto.getAligoId());
        account.setAligoKey(updateDto.getAligoId());
        return accountRepository.save(account);
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    public void deleteAll() {
        accountRepository.deleteAll();
    }
}
