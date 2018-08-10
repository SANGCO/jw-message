package dev.sangco.jwmessage.web;

import dev.sangco.jwmessage.domain.Account;
import dev.sangco.jwmessage.support.excel.ExcelReadComponent;
import dev.sangco.jwmessage.support.http.HtmlFormDataBuilder;
import dev.sangco.jwmessage.support.test.AcceptanceTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.MultiValueMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@ActiveProfiles("test")
public class ApiCompanyControllerTest extends AcceptanceTest {
    public static final Logger log = LoggerFactory.getLogger(ApiCompanyControllerTest.class);

    @Autowired
    ExcelReadComponent excelReadComponent;

    @Test
    public void updateCompanies_Test() {
        ResponseEntity<String> response = basicAuthTemplate(defaultLoginAccount)
                .postForEntity("/api/companies/update", getRequest("companyList_update"), String.class);

        log.debug("uploadCompanies_Test() - getHeaders() : " + response.getHeaders());
        log.debug("uploadCompanies_Test() - getBody() : " + response.getBody());
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

//    @Test
//    public void uploadCompanies_Test() {
//        ResponseEntity<String> response = basicAuthTemplate(defaultLoginAccount)
//                .postForEntity("/api/companies/upload", getRequest("companyList"), String.class);
//        log.debug("uploadCompanies_Test() - getHeaders() : " + response.getHeaders());
//        log.debug("uploadCompanies_Test() - getBody() : " + response.getBody());
//        assertThat(response.getStatusCode(), is(HttpStatus.OK));
//    }
//
//    @Rollback(false)
//    @Test
//    public void sendMessage_Test() {
//        ResponseEntity<String> response =
//        basicAuthTemplate(defaultLoginAccount).postForEntity("/api/companies/send", Message.builder()
//                .title("테스트문자").msg("테스트문자 본문").receiver("01047579824,01025688681,01020934806")
//                .testmode_yn("Y").build(), String.class);
//        log.debug("sendMessage_Test() - getHeaders() : " + response.getHeaders());
//        log.debug("sendMessage_Test() - getBody() : " + response.getBody());
//        assertThat(response.getStatusCode(), is(HttpStatus.OK));
//    }

    private HttpEntity<MultiValueMap<String, Object>> getRequest(String fileName) {
        return HtmlFormDataBuilder
                .multipartFormData()
                .addParameter("file", new ClassPathResource(fileName + ".xlsx")).build();
    }
}