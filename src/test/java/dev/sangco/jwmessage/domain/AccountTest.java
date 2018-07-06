package dev.sangco.jwmessage.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@Rollback
public class AccountTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Test
    public void test_Insert_accounts() {
        Account account = Account.builder()
                .accId("test")
                .password("test")
                .name("테스트")
                .phoneNumb("010123456")
                .aligoId("알리고Id")
                .aligoKey("알리고키").build();

        List<Company> companies = new ArrayList<>();

        IntStream.range(0,10).forEach(i -> {
            companies.add(Company.builder()
                    .companyName("테스트" + i)
                    .type("도매" + i)
                    .personIncharge("테스트" + i)
                    .position("테스트" + i)
                    .contactNumb("010123456" + i).build());
        });

        account.setCompany(companies);
        accountRepository.save(account);

        Account savedAccount = accountRepository.findAll().get(0);
        savedAccount.getCompany().stream().forEach(c -> {
            System.out.println(c);
        });
        assertThat(savedAccount.getCompany().size(), is(10));
    }
}