package dev.sangco.jwmessage.domain;

import dev.sangco.jwmessage.support.domain.BaseTimeEntity;
import lombok.*;
import org.apache.poi.ss.usermodel.Row;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Builder
@EqualsAndHashCode(of = "companyName", callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Company extends BaseTimeEntity {

    @Id
    @Column(unique = true, nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String personIncharge;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false)
    private String contactNumb;

    // TODO '-' 빼고 넣자
    private String salesPerson;

    private String meatCuts;

    public Company update(Company company) {
        this.type = company.getType();
        this.personIncharge = company.getPersonIncharge();
        this.position = company.getPosition();
        this.contactNumb = company.getContactNumb();
        this.salesPerson = company.getSalesPerson();
        this.meatCuts = company.getMeatCuts();
        return this;
    }

    public static Company ofRow(Row row) {
        return Company.builder()
                .companyName(row.getCell(0).getStringCellValue())
                .type(row.getCell(1).getStringCellValue())
                .personIncharge(row.getCell(2).getStringCellValue())
                .position(row.getCell(3).getStringCellValue())
                .contactNumb(row.getCell(4).getStringCellValue())
                .salesPerson(row.getCell(5).getStringCellValue())
                .meatCuts(row.getCell(6).getStringCellValue())
                .build();
   }
}