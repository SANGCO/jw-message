package dev.sangco.jwmessage.service;

import dev.sangco.jwmessage.common.AccountDuplicatedException;
import dev.sangco.jwmessage.common.AccountNotFoundException;
import dev.sangco.jwmessage.domain.Account;
import dev.sangco.jwmessage.domain.AccountDto;
import dev.sangco.jwmessage.domain.AccountRepository;
import dev.sangco.jwmessage.domain.Company;
import dev.sangco.jwmessage.domain.Role;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Account createAccount(AccountDto.Create createDto) {
        Account account = modelMapper.map(createDto, Account.class);
        String accId = createDto.getAccId();
        accountRepository.findByAccId(accId).ifPresent(s -> {
            throw new AccountDuplicatedException(accId);
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

    public Account updateAccount(String accId, AccountDto.Update updateDto) {
        Account account = findByAccId(accId);
        account.setPassword(updateDto.getPassword());
        account.setName(updateDto.getName());
        account.setPhoneNumb(updateDto.getPhoneNumb());
        account.setAligoId(updateDto.getAligoId());
        account.setAligoKey(updateDto.getAligoId());
        return accountRepository.save(account);
    }

//    public void updateAccountCompany(String accId, Company company) {
//        Account account = findByAccId(accId);
//       if (account.getCompanies().stream().noneMatch(c -> c.getCompanyName().equalsIgnoreCase(company.getCompanyName()))) {
//           accountRepository.save(account);
//       }
//       // TODO Arrays.asList() 이런식으로 리스트를 만들어서 세팅하기
//    }

    public void deleteByAccId(String accId) {
        accountRepository.deleteByAccId(accId);
    }

    public void deleteAll() {
        accountRepository.deleteAll();
    }
}
