package dev.sangco.jwmessage.web;

import dev.sangco.jwmessage.support.excel.ExcelReadComponent;
import dev.sangco.jwmessage.support.test.AcceptanceTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ApiCompanyControllerTest extends AcceptanceTest {
    public static final Logger log =  LoggerFactory.getLogger(ApiCompanyControllerTest.class);
    
    @Autowired
    ExcelReadComponent excelReadComponent;

    @Test
    public void updateCompany_Test() {
    }
}