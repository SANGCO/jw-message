package dev.sangco.jwmessage.domain;

import lombok.*;

import java.util.List;


public class CompanyDto {

    @EqualsAndHashCode
    @ToString
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Response {

        private String companyName;
        private String type;
        private String personIncharge;
        private String position;
        private String contactNumb;
    }
}
