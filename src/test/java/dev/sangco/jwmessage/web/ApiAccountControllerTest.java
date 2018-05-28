package dev.sangco.jwmessage.web;

import dev.sangco.jwmessage.common.AccountDuplicatedException;
import dev.sangco.jwmessage.domain.Account;
import dev.sangco.jwmessage.domain.AccountDto;
import dev.sangco.jwmessage.domain.AccountRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiAccountControllerTest {
    public static final Logger log = LoggerFactory.getLogger(ApiAccountControllerTest.class);

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ModelMapper modelMapper;

    @After
    public void cleanUp() {
        accountRepository.deleteAll();
    }

    @Test
    public void createAccout_Test() {
        ResponseEntity<String> response = template.postForEntity("/api/accounts/join", getAccountDtoCreate(), String.class);
        log.debug("postForEntity - getBody() : " + response.getBody());
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void createAccout_BadRequest_Test() {
        AccountDto.Create accDtoCreate = getAccountDtoCreate();
        accDtoCreate.setAccountId("");
        ResponseEntity<String> response = template.postForEntity("/api/accounts/join", accDtoCreate, String.class);
        log.debug("postForEntity - getBody() : " + response.getBody());
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        assertTrue(response.getBody().contains("bad.request"));
        assertTrue(response.getBody().contains("잘못된 요청입니다."));
        assertTrue(response.getBody().contains("아이디를 입력하세요."));
    }

    @Test
    public void createAccount_Duplicated_AccountId_Test() {
        accountRepository.save(modelMapper.map(getAccountDtoCreate(), Account.class));
        try {
            ResponseEntity<String> response = template.postForEntity("/api/accounts/join", getAccountDtoCreate(), String.class);
            log.debug("postForEntity - getBody() : " + response.getBody());
            assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
            assertTrue(response.getBody().contains("duplicated.accountId.exception"));
            assertTrue(response.getBody().contains("중복된 accountId 입니다."));
        } catch (AccountDuplicatedException e) {
            assertThat(e.getMessage(), is("test1213"));
        }
    }

    @Test
    public void createAccount_Hibernate_validation_Test() {
        try {
            accountRepository.save(Account.builder()
                    .accountId("test1213")
                    // name 없이 요청을 보내서 nullable = false가 잘 동작하는지 테스트
                    .password("123456")
                    .phoneNumb("01012345678")
                    .aligoId("알리고Id")
                    .aligoKey("알리과Key")
                    .build());
        } catch (DataIntegrityViolationException e) {
            log.debug(e.getMessage());
            log.debug("getRootCause()로 SQLException을 가지고 와서 에러정보 확인하기" + e.getRootCause().getMessage());
        }
    }

    private AccountDto.Create getAccountDtoCreate() {
        return new AccountDto.Create(
                "test1213", "123456", "123456", "테스트",
                "01012345678", "알리고Id", "알리고Key");
    }
}