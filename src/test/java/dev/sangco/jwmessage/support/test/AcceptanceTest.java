package dev.sangco.jwmessage.support.test;

import dev.sangco.jwmessage.domain.Account;
import dev.sangco.jwmessage.domain.AccountDto;
import dev.sangco.jwmessage.domain.AccountRepository;
import dev.sangco.jwmessage.domain.Role;
import dev.sangco.jwmessage.service.AccountService;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class AcceptanceTest {
    public static final Logger log =  LoggerFactory.getLogger(AcceptanceTest.class);
    
    @Autowired
    protected TestRestTemplate template;

    protected TestRestTemplate basicAuthTemplate;

    @Autowired
    protected AccountService accountService;

    @Autowired
    protected  AccountRepository accountRepository;

    @Autowired
    protected MessageSourceAccessor msa;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    public void setUp() {

        Account account = accountRepository.save(getDefaultAccount());
        basicAuthTemplate = template.withBasicAuth(account.getAccId(), account.getAccId()); // 아이디 == 비번
    }

    @After
    public void cleanUp() {
        accountRepository.deleteAll();
    }

    protected Account getDefaultAccount() {
        return Account.builder()
                .accId("default1213")
                .password(passwordEncoder.encode("default1213"))
                .name("디폴트")
                .phoneNumb(msa.getMessage("account.phoneNumb"))
                .aligoId(msa.getMessage("account.aligoId"))
                .aligoKey(msa.getMessage("account.aligoKey"))
                .role(Role.ADMIN)
                .build();
    }

    protected AccountDto.Create getCreateAccount() {
        return AccountDto.Create.builder()
                .accId("test1213")
                .password("test1213")
                .cpassword("test1213")
                .name("테스트")
                .phoneNumb(msa.getMessage("account.phoneNumb"))
                .aligoId(msa.getMessage("account.aligoId"))
                .aligoKey(msa.getMessage("account.aligoKey"))
                .build();
    }

    protected AccountDto.Update getUpdateAccount() {
        return AccountDto.Update.builder()
                .password("default1213")
                .cpassword("default1213")
                .name("업데이트")
                .phoneNumb(msa.getMessage("account.phoneNumb"))
                .aligoId(msa.getMessage("account.aligoId"))
                .aligoKey(msa.getMessage("account.aligoKey"))
                .build();
    }
}
