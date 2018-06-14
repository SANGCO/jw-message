package dev.sangco.jwmessage.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Commit
public class AccountTest {
// TODO Company에서 Account Id 값으로 해당하는 Company 가지고 오기(N + 1문제)
//    @Autowired
//    AccountRepository accountRepo;
//
//    @Autowired
//    CompanyRepository companyRepo;

//    @Test
//    public void test_Insert_accounts() {
//
//        IntStream.range(0, 10).forEach(i -> {
//            Account account = Account.builder()
//                    .accId("test" + i)
//                    .password("test" + i)
//                    .name("테스트" + i)
//                    .phoneNumb("010123456")
//                    .aligoId("알리고Id")
//                    .aligoKey("알리고키").build();
//            accountRepo.save(account);
//        });
//    }

//    @Test
//    public void test_Insert_Company() {
//
//        Account account = accountRepo.findByAccId("test0").get();
//        List<Company> companies = new ArrayList<>();
//
//        IntStream.range(0, 10).forEach(i -> {
//
//            Company company = Company.builder()
//                    .account(account)
//                    .companyName("테스트" + i)
//                    .type("도매" + i)
//                    .personIncharge("테스트" + i)
//                    .position("테스트" + i)
//                    .contactNumb("010123456" + i).build();
//            companies.add(company);
//        });
//        companyRepo.saveAll(companies);
//    }
//
//    @Test
//    public void test_Insert_Company2() {
//
//        Account account = new Account();
//        account.setAccId("test1");
//
//        IntStream.range(10, 20).forEach(i -> {
//
//            Company company = Company.builder()
//                    .account(account)
//                    .companyName("테스트" + i)
//                    .type("도매" + i)
//                    .personIncharge("테스트" + i)
//                    .position("테스트" + i)
//                    .contactNumb("010123456" + i).build();
//            companyRepo.save(company);
//        });
//    }

//    @Test
//    public void test_SelectAll_Company() {
//        Account account = new Account();
//        account.setAccId("test0");
//        companyRepo.findAllByAccount(account).stream().forEach(c -> {
//            System.out.println(c.toString());
//        });
//        account.setAccId("test1");
//        companyRepo.findAllByAccount(account).stream().forEach(c -> {
//            System.out.println(c.toString());
//        });
//    }
}