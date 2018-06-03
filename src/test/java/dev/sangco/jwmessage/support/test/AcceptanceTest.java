package dev.sangco.jwmessage.support.test;

import dev.sangco.jwmessage.domain.Account;
import dev.sangco.jwmessage.domain.AccountDto;
import dev.sangco.jwmessage.domain.AccountRepository;
import dev.sangco.jwmessage.service.AccountService;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest {

    @Autowired
    protected TestRestTemplate testRestTemplate;

    @Autowired
    protected AccountRepository accountRepository;

    @Autowired
    protected AccountService accountService;

    protected Account defaultLoginAccount;
    protected Account defaultAnotherAccount;

    @Before
    public void setUp() {
        defaultLoginAccount = accountService.createAccount(getLoginAccount());
        defaultAnotherAccount = accountService.createAccount(getAnotherAccount());
    }

    @After
    public void cleanUp() {
        accountRepository.deleteAll();
    }

    protected TestRestTemplate basicAuthTemplate(Account paramAccount) {
        return testRestTemplate.withBasicAuth(paramAccount.getAccountId(), "123456");
    }

    protected AccountDto.Create getLoginAccount() {
        return new AccountDto.Create("loginAccount", "123456", "123456", "로그인어카운트",
                "01012345678", "로그인알리고ID", "로그인알리고Key");
    }

    protected AccountDto.Create getAnotherAccount() {
        return new AccountDto.Create("anotherAccount", "123456", "123456", "어나더어카운트",
                "01012345678", "어나더알리고ID", "어나더알리고Key");
    }
}
