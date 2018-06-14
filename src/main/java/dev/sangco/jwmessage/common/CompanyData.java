package dev.sangco.jwmessage.common;

import lombok.Data;

import java.util.List;

@Data
public class CompanyData {

    private List<CompanyDataRows> data;

    @Data
    public static class CompanyDataRows {
        private long id;
        private String companyName;
        private String type;
        private String personIncharge;
        private String position;
        private String contactNumb;
    }
}





