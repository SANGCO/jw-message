package dev.sangco.jwmessage.domain;

import lombok.*;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;


public class CompanyDto {

    @EqualsAndHashCode
    @ToString
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class ResponseS {

        private String companyName;
        private String type;
        private String personIncharge;
        private String position;
        private String contactNumb;
    }

    @EqualsAndHashCode
    @ToString
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class ResponseO {

        private String companyName;
        private TypeOfBiz type;
        private String personIncharge;
        private String position;
        private String contactNumb;
    }

    @EqualsAndHashCode
    @ToString
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
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
                .companyName(row.getCell(1).getStringCellValue())
                .type(row.getCell(2).getStringCellValue())
                .personIncharge(row.getCell(3).getStringCellValue())
                .position(row.getCell(4).getStringCellValue())
                .contactNumb(row.getCell(5).getStringCellValue())
                .salesPerson(row.getCell(6).getStringCellValue())
                .meatCuts(row.getCell(7).getStringCellValue())
                .build();
    }
}
