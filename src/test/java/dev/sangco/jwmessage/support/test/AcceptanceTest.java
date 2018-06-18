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
import org.springframework.boot.web.server.LocalServerPort;
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

    @LocalServerPort
    protected int port;

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
        return testRestTemplate.withBasicAuth(paramAccount.getAccId(), "123456");
    }

    // TODO (처리요망) 여기도 키 값과 아이디가 들어간다.
    protected AccountDto.Create getLoginAccount() {
        return new AccountDto.Create("loginAccount", "123456", "123456", "로그인어카운트",
                "01047579824", "jwpro", "xemrqh2zv51u88p3o3b22z6yjmn8tv5x");
    }

    protected AccountDto.Create getAnotherAccount() {
        return new AccountDto.Create("anotherAccount", "123456", "123456", "어나더어카운트",
                "01047579824", "jwpro", "xemrqh2zv51u88p3o3b22z6yjmn8tv5x");
    }
    protected AccountDto.Create getAccountDtoCreate() {
        return new AccountDto.Create(
                "test1213", "123456", "123456", "테스트",
                "01047579824", "jwpro", "xemrqh2zv51u88p3o3b22z6yjmn8tv5x");
    }

    protected AccountDto.Update getAccountDtoUpdate() {
        return new AccountDto.Update(
                "123456", "123456", "업데이트테스트",
                "01012345678", "jwpro", "xemrqh2zv51u88p3o3b22z6yjmn8tv5x");
    }
}
