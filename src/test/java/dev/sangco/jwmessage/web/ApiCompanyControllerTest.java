package dev.sangco.jwmessage.web;

import dev.sangco.jwmessage.support.excel.ExcelReadComponent;
import dev.sangco.jwmessage.support.test.AcceptanceTest;
import dev.sangco.jwmessage.support.test.HtmlFormDataBuilder;
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


public class ApiCompanyControllerTest extends AcceptanceTest {
    public static final Logger log = LoggerFactory.getLogger(ApiCompanyControllerTest.class);

    @Autowired
    ExcelReadComponent excelReadComponent;

    @Test
    public void uploadCompanies_Test() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder
                .multipartFormData()
                .addParameter("file", new ClassPathResource("test.xlsx")).build();



        ResponseEntity<String> response = basicAuthTemplate(defaultLoginAccount)
                .postForEntity("/api/companies/upload", request, String.class);



        log.debug("uploadCompanies_Test() - getBody() : " + response.getBody());
        log.debug("uploadCompanies_Test() - getHeaders() : " + response.getHeaders());
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }
}