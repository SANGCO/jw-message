package dev.sangco.jwmessage.common;

import lombok.Data;

import java.util.List;

@Data
public class ErrorResponse {

    private String code;

    private String message;

    private List<fieldError> fieldErrors;

    @Data
    public static class fieldError {

        private String field;

        private String message;
    }
}





