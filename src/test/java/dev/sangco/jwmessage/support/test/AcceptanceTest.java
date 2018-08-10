package dev.sangco.jwmessage.support.test;

import dev.sangco.jwmessage.domain.Account;
import dev.sangco.jwmessage.domain.AccountDto;
import dev.sangco.jwmessage.domain.AccountRepository;
import dev.sangco.jwmessage.domain.Role;
import dev.sangco.jwmessage.service.AccountService;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest {
    protected static final String DEFAULT_LOGIN_ACCOUNT = "test1234";

    @Autowired
    protected TestRestTemplate testRestTemplate;

    @Autowired
    protected AccountRepository accountRepository;

    @Autowired
    protected AccountService accountService;

    @Autowired
    private MessageSourceAccessor msa;

    @LocalServerPort
    protected int port;

    protected Account defaultLoginAccount;

    protected Account defaultAnotherAccount;

    @Before
    public void setUp() {
        defaultLoginAccount = accountService.createAccount(getLoginAccount());
//        defaultAnotherAccount = accountService.createAccount(getAnotherAccount());
    }

    protected TestRestTemplate basicAuthTemplate(Account paramAccount) {
        return testRestTemplate.withBasicAuth(paramAccount.getAccId(), "123456");
    }

    private AccountDto.Create getLoginAccount() {
        return new AccountDto.Create("loginAccount", "123456", "123456", "로그인어카운트",
                msa.getMessage("phoneNumb"), msa.getMessage("aligoId"), msa.getMessage("aligoKey"));
    }

    protected AccountDto.Create getAnotherAccount() {
        return new AccountDto.Create("anotherAccount", "123456", "123456", "어나더어카운트",
                msa.getMessage("phoneNumb"), msa.getMessage("aligoId"), msa.getMessage("aligoKey"));
    }

    protected AccountDto.Create getAccountDtoCreate() {
        return new AccountDto.Create(
                "test1213", "123456", "123456", "테스트",
                msa.getMessage("phoneNumb"), msa.getMessage("aligoId"), msa.getMessage("aligoKey"));
    }

    protected AccountDto.Update getAccountDtoUpdate() {
        return new AccountDto.Update(
                "123456", "123456", "업데이트테스트",
                msa.getMessage("phoneNumb"), msa.getMessage("aligoId"), msa.getMessage("aligoKey"));
    }
}
