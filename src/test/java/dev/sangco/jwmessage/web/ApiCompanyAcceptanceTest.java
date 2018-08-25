package dev.sangco.jwmessage.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sangco.jwmessage.domain.*;
import dev.sangco.jwmessage.support.excel.ExcelReadComponent;
import dev.sangco.jwmessage.support.http.HtmlFormDataBuilder;
import dev.sangco.jwmessage.support.test.AcceptanceTest;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpMethod.PUT;

public class ApiCompanyAcceptanceTest extends AcceptanceTest {

    @Autowired
    private TypeOfBIzRepository typeOfBIzRepository;

    @Autowired
    private SalesPersonRepository salesPersonRepository;

    @Autowired
    private MeatCutRepository meatCutRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void uploadCompanies_test() {
        ResponseEntity<String> response = basicAuthTemplate
                .postForEntity("/api/companies", getRequest("companyList"), String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        log(response);
    }

    @Test
    public void updateCompanies_test() throws IOException {
        ResponseEntity<String> response = basicAuthTemplate
                .exchange("/api/companies", PUT, getRequest("companyList"), String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(typeOfBIzRepository.count(), is(5L));
        assertThat(salesPersonRepository.count(), is(2L));
        assertThat(meatCutRepository.count(), is(13L));
        log(response);
        updateCompanies_deleteEarlierData_test();
        searchCompanyData_Test();
    }

    private void updateCompanies_deleteEarlierData_test() {
        ResponseEntity<String> response = basicAuthTemplate
                .exchange("/api/companies", PUT, getRequest("companyList_update"), String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(typeOfBIzRepository.count(), is(2L));
        assertThat(salesPersonRepository.count(), is(1L));
        assertThat(meatCutRepository.count(), is(6L));
        log(response);
    }

    private void searchCompanyData_Test() throws IOException {
        ResponseEntity<String> response = basicAuthTemplate
                .postForEntity("/api/companies/search", CompanyDto.Request.builder()
                        .type("프랜차이즈").salesPerson("010-1234-5678").meatCuts("일반갈비").build(), String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        List<CompanyDto.ResponseS> companies = objectMapper.readValue(response.getBody(), new TypeReference<List<CompanyDto.ResponseS>>(){});
        assertThat(companies.size(), is(2));
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        log(response);
    }

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