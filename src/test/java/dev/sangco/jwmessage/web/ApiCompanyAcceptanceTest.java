package dev.sangco.jwmessage.web;

import dev.sangco.jwmessage.domain.MeatCutRepository;
import dev.sangco.jwmessage.domain.SalesPerson;
import dev.sangco.jwmessage.domain.SalesPersonRepository;
import dev.sangco.jwmessage.domain.TypeOfBIzRepository;
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
import org.springframework.util.MultiValueMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ApiCompanyAcceptanceTest extends AcceptanceTest {
    public static final Logger log = LoggerFactory.getLogger(ApiCompanyAcceptanceTest.class);

    @Autowired
    private ExcelReadComponent excelReadComponent;

    @Autowired
    private TypeOfBIzRepository typeOfBIzRepository;

    @Autowired
    private SalesPersonRepository salesPersonRepository;

    @Autowired
    private MeatCutRepository meatCutRepository;

    @Test
    public void uploadCompanies_Test() {
        ResponseEntity<String> response = basicAuthTemplate
                .postForEntity("/api/companies/upload", getRequest("companyList"), String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        log.debug("header : {}", response.getHeaders());
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void updateCompanies_Test() {
        ResponseEntity<String> response = basicAuthTemplate
                .postForEntity("/api/companies/update", getRequest("companyList"), String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(typeOfBIzRepository.count(), is(5));
        assertThat(salesPersonRepository.count(), is(1));
        assertThat(meatCutRepository.count(), is(13));
        log.debug("header : {}", response.getHeaders());
        log.debug("body : {}", response.getBody());
    }

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