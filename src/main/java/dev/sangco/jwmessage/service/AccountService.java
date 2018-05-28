package dev.sangco.jwmessage.service;

import dev.sangco.jwmessage.common.AccountDuplicatedException;
import dev.sangco.jwmessage.domain.Account;
import dev.sangco.jwmessage.domain.AccountDto;
import dev.sangco.jwmessage.domain.AccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Transactional
@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MessageSourceAccessor msa;

    public Account createAccount(AccountDto.Create accDtoCreate) {
        Account account = modelMapper.map(accDtoCreate, Account.class);
        String accountId = accDtoCreate.getAccountId();
        if(accountRepository.findByAccountId(accountId) != null) {
            throw new AccountDuplicatedException(accountId);
        }

        return accountRepository.save(account);
    }
}
