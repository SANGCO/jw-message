package dev.sangco.jwmessage.web;

import com.querydsl.core.types.Predicate;
import dev.sangco.jwmessage.common.AccountDuplicatedException;
import dev.sangco.jwmessage.domain.Account;
import dev.sangco.jwmessage.domain.QAccount;
import dev.sangco.jwmessage.domain.Role;
import dev.sangco.jwmessage.support.http.HtmlFormDataBuilder;
import dev.sangco.jwmessage.support.test.AcceptanceTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.OK;



public class AccountAcceptanceTest extends AcceptanceTest {
    public static final Logger log =  LoggerFactory.getLogger(AccountAcceptanceTest.class);

    @Test
    public void view_joinForm_test() {
        ResponseEntity<String> response = template.getForEntity("/accounts/join", String.class);
        assertThat(response.getStatusCode(), is(OK));
        assertTrue(response.getBody().contains("회원가입"));
    }

    @Test
    public void createAccount_role_passwordEncode_test() {
        ResponseEntity<String> response = template.postForEntity("/accounts/join", getRequest("test1213"), String.class);
        Account account = accountService.findByAccId("test1213");
        assertThat(response.getStatusCode(), is(FOUND));
        assertThat(account.getRole(), is(Role.USER));
        assertFalse(account.getPassword().equalsIgnoreCase("test1213"));
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void createAccount_cpasswordException_test() {
        HttpEntity<MultiValueMap<String, Object>> request = getRequest("test1213");
        request.getBody().set("cpassword", "test9999");
        ResponseEntity<String> response = template.postForEntity("/accounts/join", request, String.class);
        assertThat(response.getStatusCode(), is(OK));
        assertTrue(response.getBody().contains("비밀번호와 비밀번호 확인 같지 않습니다."));
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void createAccout_BadRequest_Test() {
        ResponseEntity<String> response = template.postForEntity("/accounts/join", getRequest(""), String.class);
        assertThat(response.getStatusCode(), is(OK));
        assertTrue(response.getBody().contains("아이디는 최소4자 ~ 최대21자 입니다."));
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void createAccount_Duplicated_AccountId_Test() {
        ResponseEntity<String> response = template.postForEntity("/accounts/join", getRequest("default1213"), String.class);
        assertThat(response.getStatusCode(), is(OK));
        assertTrue(response.getBody().contains("default1213 중복된 아이디입니다."));
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void view_login_test() {
        ResponseEntity<String> response = template.getForEntity("/accounts/login", String.class);
        assertThat(response.getStatusCode(), is(OK));
        assertTrue(response.getBody().contains("환영합니다!"));
    }

    @Test
    public void view_accessDenied_test() {
        ResponseEntity<String> response = template.getForEntity("/accounts/accessDenied", String.class);
        assertThat(response.getStatusCode(), is(OK));
        assertTrue(response.getBody().contains("접근권한이 없습니다."));
    }

    @Test
    public void view_updateForm_test() {
        ResponseEntity<String> response = basicAuthTemplate.getForEntity("/accounts/update", String.class);
        assertThat(response.getStatusCode(), is(OK));
        assertTrue(response.getBody().contains("회원정보 수정"));
    }

    // TODO !accId.equalsIgnoreCase(principal.getName()) 로그인 사용자를 테스트 하는건 어떻게 해야할까?
//    @Test
//    public void update_test() {
//        ResponseEntity<String> response = basicAuthTemplate.postForEntity("/accounts/update/default1213", getUpdateAccount(), String.class);
//        assertThat(response.getStatusCode(), is(FOUND));
//        log.debug("body : {}", response.getBody());
//    }

    @Test
    public void crud() {
        QAccount account = QAccount.account;
        Predicate predicate = account.accId.containsIgnoreCase("default");
        Optional<Account> one = accountRepository.findOne(predicate);
        assertTrue(one.isPresent());
    }

    private HttpEntity<MultiValueMap<String, Object>> getRequest(String param) {
        return HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("accId", param).addParameter("password", param)
                .addParameter("cpassword", param).addParameter("name", "테스트")
                .addParameter("phoneNumb", msa.getMessage("account.phoneNumb"))
                .addParameter("aligoId", msa.getMessage("account.aligoId"))
                .addParameter("aligoKey", msa.getMessage("account.aligoKey"))
                .build();
    }
}