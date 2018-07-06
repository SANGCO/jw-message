package dev.sangco.jwmessage.web;

import dev.sangco.jwmessage.common.AccountDuplicatedException;
import dev.sangco.jwmessage.domain.Account;
import dev.sangco.jwmessage.domain.AccountDto;
import dev.sangco.jwmessage.support.test.AcceptanceTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;

import java.net.URI;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.PUT;

public class ApiAccountAcceptanceTest extends AcceptanceTest {
    public static final Logger log = LoggerFactory.getLogger(ApiAccountAcceptanceTest.class);

    @Test
    public void createAccout_Test() {
        ResponseEntity<String> response = testRestTemplate.postForEntity("/api/accounts/join", getAccountDtoCreate(), String.class);
        log.debug("createAccout_Test() - getBody() : " + response.getBody());
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void createAccout_BadRequest_Test() {
        AccountDto.Create accDtoCreate = getAccountDtoCreate();
        accDtoCreate.setAccId("");
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
            assertThat(accountDto.getAccId(), is(e.getMessage()));
        }
    }

//    @Test
//    public void role_Admin_Test() {
//        defaultLoginAccount.setRole(Role.ADMIN);
//        accountRepository.save(defaultLoginAccount);
//        ResponseEntity<String> response = basicAuthTemplate(defaultLoginAccount).getForEntity("/admin/test", String.class);
//        log.debug("role_Admin_Test() - getBody() : " + response.getBody());
//        assertThat(response.getStatusCode(), is(HttpStatus.OK));
//        assertTrue(response.getBody().contains("시큐러티 어드민 권한 테스트 중"));
//    }

    @Test
    public void getAccount_Test() {
        ResponseEntity<String> response = basicAuthTemplate(defaultLoginAccount)
                .getForEntity(("/api/accounts/" + defaultLoginAccount.getAccId() + ""), String.class);
        log.debug("getAccount_Test() - getBody() : " + response.getBody());
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertTrue(response.getBody().contains(defaultLoginAccount.getAccId()));
    }

    @Test
    public void getAccounts_Test() {
        ResponseEntity<String> response = basicAuthTemplate(defaultLoginAccount).getForEntity("/api/accounts", String.class);
        log.debug("getAccounts_Test() - getBody() : " + response.getBody());
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertTrue(response.getBody().contains(defaultLoginAccount.getAccId()));
    }

    @Test
    public void updateAccount_Test() {
        String accId = defaultLoginAccount.getAccId();
        basicAuthTemplate(defaultLoginAccount)
                .put(String.format("/api/accounts/%s", accId), getAccountDtoUpdate());
        assertThat(accountService.findByAccId(accId).getName(), is(getAccountDtoUpdate().getName()));
    }

    @Test
    public void updateAccount_Validation_Test() {
        String accId = defaultLoginAccount.getAccId();
        URI uri = URI.create("http://localhost:" + port + "/api/accounts/" + accId);

        AccountDto.Update updateDto = getAccountDtoUpdate();
        updateDto.setName("");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Account> entity = new HttpEntity(updateDto, headers);

        ResponseEntity<String> response = basicAuthTemplate(defaultLoginAccount).exchange(uri, PUT, entity, String.class);
        log.debug("updateAccount_Validation_Test() - getBody() : " + response.getBody());
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        assertTrue(response.getBody().contains("이름을 입력해주세요."));
        assertTrue(response.getBody().contains("Validation failed for object='update'"));
    }

    @Test
    public void updateAccount_UnAuthentication_Test() {
        String accId = defaultAnotherAccount.getAccId();
        URI uri = URI.create("http://localhost:" + port + "/api/accounts/" + accId);

        AccountDto.Update updateDto = getAccountDtoUpdate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Account> entity = new HttpEntity(updateDto, headers);

        ResponseEntity<String> response = basicAuthTemplate(defaultLoginAccount).exchange(uri, PUT, entity, String.class);
        log.debug("updateAccount_UnAuthentication_Test() - getBody() : " + response.getBody());
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        assertTrue(response.getBody().contains("UnAuthentication.exception"));
        assertTrue(response.getBody().contains("[ anotherAccount ] 권한이 없는 사용자 입니다."));
    }

    @Test
    public void deleteAccount_Test() {
        String accId = defaultLoginAccount.getAccId();
        URI uri = URI.create("http://localhost:" + port + "/api/accounts/" + accId);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Account> entity = new HttpEntity(headers);

        ResponseEntity<String> response = basicAuthTemplate(defaultLoginAccount).exchange(uri, DELETE, entity, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
    }
}