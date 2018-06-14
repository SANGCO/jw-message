package dev.sangco.jwmessage.common;

import lombok.Data;

import java.util.List;

@Data
public class ErrorResponse {

    private String code;

    private String message;

    private List<FieldError> FieldErrors;

    @Data
    public static class FieldError {

        private String field;

        private String message;
    }
}





