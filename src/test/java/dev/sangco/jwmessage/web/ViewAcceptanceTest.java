package dev.sangco.jwmessage.web;

import dev.sangco.jwmessage.support.test.AcceptanceTest;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.springframework.http.HttpStatus.OK;

public class ViewAcceptanceTest extends AcceptanceTest {

    @Test
    public void index_test() {
        ResponseEntity<String> response = template.getForEntity("/", String.class);
        assertThat(response.getStatusCode(), is(OK));
        assertTrue(response.getBody().contains("이전 사진"));
    }

    @Test
    public void view_updateForm_test() {
        ResponseEntity<String> response = basicAuthTemplate.getForEntity("/message/form", String.class);
        assertThat(response.getStatusCode(), is(OK));
        assertTrue(response.getBody().contains("거래처 리스트"));
    }
}