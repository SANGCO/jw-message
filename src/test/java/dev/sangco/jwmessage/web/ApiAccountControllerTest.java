package dev.sangco.jwmessage.web;

import dev.sangco.jwmessage.common.ErrorResponse;
import dev.sangco.jwmessage.domain.AccountDto;
import dev.sangco.jwmessage.domain.AccountRepository;
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
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiAccountControllerTest {
    public static final Logger log =  LoggerFactory.getLogger(ApiAccountControllerTest.class);

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private AccountRepository accountRepository;

    @After
    public void cleanUp() {
        accountRepository.deleteAll();
    }

    @Test
    public void createAccout_Test() {
        AccountDto.Create accDtoCreate = new AccountDto.Create(
                "test1213", "123456", "123456", "테스트",
                "01012345678", "알리고Id", "알리고Key");

        ResponseEntity<String> response = template.postForEntity("/api/accounts/join", accDtoCreate, String.class);
        log.debug("postForEntity - getBody() : " + response.getBody());
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void createAccout_BadRequest_Test() {
        AccountDto.Create accDtoCreate = new AccountDto.Create(
                "", "123456", "123456", "테스트",
                "01012345678", "알리고Id", "알리고Key");

        ResponseEntity<String> response = template.postForEntity("/api/accounts/join", accDtoCreate, String.class);
        log.debug("postForEntity - getBody() : " + response.getBody());
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        assertTrue(response.getBody().contains("잘못된 요청입니다."));
        assertTrue(response.getBody().contains("아이디를 입력하세요."));
    }
}