package dev.sangco.jwmessage.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class AccountTest {

    @Test
    public void account_update_test() {
        Account account = getAccount().update(getUpdateAccount());
        assertThat(account.getName(), is("업데이트"));
    }

    private Account getAccount() {
        return Account.builder()
                .accId("test1213")
                .password("test1213")
                .name("테스트")
                .phoneNumb("12345678")
                .aligoId("알리고ID")
                .aligoKey("알리고Key")
                .role(Role.USER)
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