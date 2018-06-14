package dev.sangco.jwmessage.domain;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AccountDtoValidationTest {
    public static final Logger log = LoggerFactory.getLogger(AccountDtoValidationTest.class);

    @Autowired
    private AccountRepository accountRepository;

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void when_accountId_is_Empty() {
        AccountDto.Create accDtoCreate = new AccountDto.Create(
                "", "123456", "123456", "테스트",
                "01012345678", "알리고Id", "알리고Key");

        Set<ConstraintViolation<AccountDto.Create>> constraintViolations = validator.validate(accDtoCreate);

        for (ConstraintViolation<AccountDto.Create> constraintViolation: constraintViolations) {
            log.debug("violation error message : {}", constraintViolation.getMessage());
        }
        assertThat(constraintViolations.size(), is(2));
        // violation error message : 아이디는 최소4자 ~ 최대21자 입니다.
        // violation error message : 아이디를 입력하세요.
    }
}