package dev.sangco.jwmessage.service;

import dev.sangco.jwmessage.common.CpasswordNotMatchException;
import dev.sangco.jwmessage.domain.Account;
import dev.sangco.jwmessage.domain.AccountDto;
import dev.sangco.jwmessage.domain.AccountRepository;
import dev.sangco.jwmessage.domain.Role;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @After
    public void cleanUp() {
        accountRepository.deleteAll();
    }

    @Test
    public void createAccount_role_passwordEncode_test() {
        Account account = accountService.createAccount(getCreateAccount());

        assertThat(account.getRole(), is(Role.USER));
        assertFalse(account.getPassword().equalsIgnoreCase("test1213"));
        assertThat(account.getAccId(), is(accountRepository.findAll().get(0).getAccId()));
    }

    @Test(expected = CpasswordNotMatchException.class)
    public void createAccount_passwordException_test() {
        AccountDto.Create cAccount = getCreateAccount();
        cAccount.setCpassword("test");
        accountService.createAccount(cAccount);
    }

    @Test
    public void updateAccount_test() {
        accountService.createAccount(getCreateAccount());
        Account account = accountService.updateAccount("test1213", getUpdateAccount());
        assertThat(account.getName(), is("업데이트"));
    }

    @Test(expected = CpasswordNotMatchException.class)
    public void updateAccount_passwordException_test() {
        accountService.createAccount(getCreateAccount());
        AccountDto.Update uAccount = getUpdateAccount();
        uAccount.setCpassword("test");
        accountService.updateAccount("test1213", uAccount);
    }

    private AccountDto.Create getCreateAccount() {
        return AccountDto.Create.builder()
                .accId("test1213")
                .password("test1213")
                .cpassword("test1213")
                .name("테스트")
                .phoneNumb("01012345678")
                .aligoId("알리고ID")
                .aligoKey("알리고Key")
                .build();
    }

    private AccountDto.Update getUpdateAccount() {
        return AccountDto.Update.builder()
                .password("test1213")
                .cpassword("test1213")
                .name("업데이트")
                .phoneNumb("01012345678")
                .aligoId("알리고ID")
                .aligoKey("알리고Key")
                .build();
    }
}