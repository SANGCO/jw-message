package dev.sangco.jwmessage.domain;

import lombok.*;

import java.util.List;

@Data
public class CompanyDto {

    private List<CompanyDto.Response> data;

    @Data
    public static class Response {

        private String companyName;
        private String type;
        private String personIncharge;
        private String position;
        private String contactNumb;
    }
}
