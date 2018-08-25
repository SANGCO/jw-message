package dev.sangco.jwmessage.domain;

import lombok.*;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;


public class CompanyDto {

    @EqualsAndHashCode
    @ToString
    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseS {
        private String companyName;
        private String type;
        private String personIncharge;
        private String position;
        private String contactNumb;
        private String salesPerson;
    }

    @EqualsAndHashCode
    @ToString
    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private String type;
        private String salesPerson;
        private String meatCuts;
    }

    @EqualsAndHashCode
    @ToString
    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Create {
        private String companyName;
        private String type;
        private String personIncharge;
        private String position;
        private String contactNumb;
        private String salesPerson;
        private String meatCuts;
    }

    public static CompanyDto.Create ofRow(Row row) {
        return CompanyDto.Create.builder()
                .companyName(row.getCell(1).getStringCellValue().trim())
                .type(row.getCell(2).getStringCellValue().trim())
                .personIncharge(row.getCell(3).getStringCellValue().trim())
                .position(row.getCell(4).getStringCellValue().trim())
                .contactNumb(row.getCell(5).getStringCellValue().trim())
                .salesPerson(row.getCell(6).getStringCellValue().trim())
                .meatCuts(row.getCell(7).getStringCellValue().trim())
                .build();
    }
}
