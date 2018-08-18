package dev.sangco.jwmessage.service;

import dev.sangco.jwmessage.common.AccountDuplicatedException;
import dev.sangco.jwmessage.common.AccountNotFoundException;
import dev.sangco.jwmessage.common.CpasswordNotMatchException;
import dev.sangco.jwmessage.domain.Account;
import dev.sangco.jwmessage.domain.AccountDto;
import dev.sangco.jwmessage.domain.AccountRepository;
import dev.sangco.jwmessage.domain.Role;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    protected MessageSourceAccessor msa;

    public Account createAccount(AccountDto.Create cAccount) {
        isCpasswordMatch(cAccount.getPassword(), cAccount.getCpassword());
        Account account = modelMapper.map(cAccount, Account.class);
        String accId = cAccount.getAccId();

        accountRepository.findByAccId(accId).ifPresent(s -> {
            throw new AccountDuplicatedException(accId + " " + msa.getMessage("e.accountDupl.m"));
        });

        account.setRole(Role.USER);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    public Account findByAccId(String accId) {
        return accountRepository.findByAccId(accId).orElseThrow(() -> new AccountNotFoundException(accId));
    }

    public Page<Account> findAll(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    public Account updateAccount(String accId, AccountDto.Update uAccount) {
        isCpasswordMatch(uAccount.getPassword(), uAccount.getCpassword());
        Account account = findByAccId(accId).update(uAccount);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    public void deleteByAccId(String accId) {
        accountRepository.deleteByAccId(accId);
    }

    private void isCpasswordMatch(String password, String cpassword) {
        if (!password.equalsIgnoreCase(cpassword)) {
            throw new CpasswordNotMatchException(msa.getMessage("e.cpasswordNotMatch.m"));
        }
    }
}
