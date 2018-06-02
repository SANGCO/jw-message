package dev.sangco.jwmessage.web;

import dev.sangco.jwmessage.common.AccountDuplicatedException;
import dev.sangco.jwmessage.domain.Account;
import dev.sangco.jwmessage.domain.AccountDto;
import dev.sangco.jwmessage.domain.AccountRepository;
import dev.sangco.jwmessage.domain.Role;
import dev.sangco.jwmessage.service.AccountService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiAccountAcceptanceTest {
    public static final Logger log = LoggerFactory.getLogger(ApiAccountAcceptanceTest.class);

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @After
    public void cleanUp() {
        accountService.deleteAll();
    }

    @Test
    public void createAccout_Test() {
        ResponseEntity<String> response = testRestTemplate.postForEntity("/api/accounts/join", getAccountDtoCreate(), String.class);
        log.debug("postForEntity - getBody() : " + response.getBody());
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void createAccout_BadRequest_Test() {
        AccountDto.Create accDtoCreate = getAccountDtoCreate();
        accDtoCreate.setAccountId("");
        ResponseEntity<String> response = testRestTemplate.postForEntity("/api/accounts/join", accDtoCreate, String.class);
        log.debug("response body : ", response.getBody());
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        assertTrue(response.getBody().contains("badRequest"));
        assertTrue(response.getBody().contains("잘못된 요청입니다."));
        assertTrue(response.getBody().contains("아이디를 입력하세요."));
    }

    @Test
    public void createAccount_Duplicated_AccountId_Test() {
        AccountDto.Create accountDto = getAccountDtoCreate();
        try {
            accountService.createAccount(accountDto);
            ResponseEntity<String> response = testRestTemplate.postForEntity("/api/accounts/join", accountDto, String.class);
        } catch (AccountDuplicatedException e) {
            assertThat(accountDto.getAccountId(), is(e.getMessage()));
        }
    }

    @Test
    public void test() {
        AccountDto.Create accountDto = getAccountDtoCreate();
        Account account = accountService.createAccount(accountDto);
        account.setRole(Role.ADMIN);
        accountRepository.save(account);
        ResponseEntity<String> response = testRestTemplate.withBasicAuth("test1213", "123456").getForEntity("/test", String.class);
        log.debug(response.getBody());
    }

    private AccountDto.Create getAccountDtoCreate() {
        return new AccountDto.Create(
                "test1213", "123456", "123456", "테스트",
                "01012345678", "알리고Id", "알리고Key");
    }
}