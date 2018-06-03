package dev.sangco.jwmessage.web;

import dev.sangco.jwmessage.common.AccountDuplicatedException;
import dev.sangco.jwmessage.domain.Account;
import dev.sangco.jwmessage.domain.AccountDto;
import dev.sangco.jwmessage.domain.Role;
import dev.sangco.jwmessage.support.test.AcceptanceTest;
import org.junit.After;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;

import java.net.URI;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.http.HttpMethod.*;

public class ApiAccountAcceptanceTest extends AcceptanceTest {
    public static final Logger log = LoggerFactory.getLogger(ApiAccountAcceptanceTest.class);

    @After
    public void cleanUp() {
        accountService.deleteAll();
    }

    @Test
    public void createAccout_Test() {
        ResponseEntity<String> response = testRestTemplate.postForEntity("/api/accounts/join", getAccountDtoCreate(), String.class);
        log.debug("createAccout_Test() - getBody() : " + response.getBody());
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void createAccout_BadRequest_Test() {
        AccountDto.Create accDtoCreate = getAccountDtoCreate();
        accDtoCreate.setAccountId("");
        ResponseEntity<String> response = testRestTemplate.postForEntity("/api/accounts/join", accDtoCreate, String.class);
        log.debug("createAccout_BadRequest_Test() - getBody() : " + response.getBody());
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
            log.debug("createAccount_Duplicated_AccountId_Test() - getBody() : " + response.getBody());
            assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        } catch (AccountDuplicatedException e) {
            assertThat(accountDto.getAccountId(), is(e.getMessage()));
        }
    }

    @Test
    public void role_Admin_Test() {
        defaultLoginAccount.setRole(Role.ADMIN);
        accountRepository.save(defaultLoginAccount);
        ResponseEntity<String> response = basicAuthTemplate(defaultLoginAccount).getForEntity("/admin/test", String.class);
        log.debug("role_Admin_Test() - getBody() : " + response.getBody());
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertTrue(response.getBody().contains("시큐러티 어드민 권한 테스트 중"));
    }

    @Test
    public void getAccount_Test() {
        ResponseEntity<String> response = basicAuthTemplate(defaultLoginAccount)
                .getForEntity(("/api/accounts/" + defaultLoginAccount.getId() + ""), String.class);
        log.debug("getAccount_Test() - getBody() : " + response.getBody());
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertTrue(response.getBody().contains(defaultLoginAccount.getAccountId()));
    }

    @Test
    public void getAccounts_Test() {
        ResponseEntity<String> response = basicAuthTemplate(defaultLoginAccount).getForEntity("/api/accounts", String.class);
        log.debug("getAccounts_Test() - getBody() : " + response.getBody());
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void updateAccount_Test() {
        Long id = defaultLoginAccount.getId();
        basicAuthTemplate(defaultLoginAccount)
                .put(String.format("/api/accounts/%d", id), getAccountDtoUpdate());
        assertThat(accountService.findById(id).getName(), is(getAccountDtoUpdate().getName()));
    }

    @Test
    public void updateAccount_Validation_Test() {
        Long id = defaultLoginAccount.getId();
        URI uri = URI.create("http://localhost:" + port + "/api/accounts/" + id);

        AccountDto.Update updateDto = getAccountDtoUpdate();
        updateDto.setName("");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Account> entity = new HttpEntity(updateDto, headers);

        ResponseEntity<String> response = basicAuthTemplate(defaultLoginAccount).exchange(uri, PUT, entity, String.class);
        log.debug("updateAccount_Validation_Test() - getBody() : " + response.getBody());
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        assertTrue(response.getBody().contains("이름을 입력해주세요."));
        assertTrue(response.getBody().contains("Validation failed for object='update'."));
    }

    @Test
    public void updateAccount_UnAuthentication_Test() {
        Long id = defaultAnotherAccount.getId();
        URI uri = URI.create("http://localhost:" + port + "/api/accounts/" + id);

        AccountDto.Update updateDto = getAccountDtoUpdate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Account> entity = new HttpEntity(updateDto, headers);

        ResponseEntity<String> response = basicAuthTemplate(defaultLoginAccount).exchange(uri, PUT, entity, String.class);
        log.debug("updateAccount_UnAuthentication_Test() - getBody() : " + response.getBody());
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        assertTrue(response.getBody().contains("UnAuthentication.exception"));
        assertTrue(response.getBody().contains("권한이 없는 사용자 입니다."));
    }

    private AccountDto.Create getAccountDtoCreate() {
        return new AccountDto.Create(
                "test1213", "123456", "123456", "테스트",
                "01012345678", "알리고Id", "알리고Key");
    }

    private AccountDto.Update getAccountDtoUpdate() {
        return new AccountDto.Update(
                "123456", "123456", "업데이트테스트",
                "01012345678", "알리고Id", "알리고Key");
    }
}