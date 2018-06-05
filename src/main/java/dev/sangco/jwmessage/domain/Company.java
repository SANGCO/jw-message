package dev.sangco.jwmessage.domain;

import lombok.*;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
public class Company {

    @Id
    @Column
    @GeneratedValue
    private Long id;

    @CreatedDate
    private LocalDateTime createDate;

    @Column(unique = true, nullable = false)
    String companyName;

    @Column(nullable = false)
    String type;

    @Column(nullable = false)
    String personIncharge;

    @Column(nullable = false)
    String position;

    @Column(nullable = false)
    String contactNumb;

    // TODO 데이터가 많아지면 쿼리로 Company에서 해당하는 Account를 찾는 식으로
//    @ManyToMany(mappedBy = "companies")
//    private Set<Account> accounts = new HashSet<>();

    @Builder
    public Company(String companyName, String type, String personIncharge, String position, String contactNumb) {
        this.companyName = companyName;
        this.type = type;
        this.personIncharge = personIncharge;
        this.position = position;
        this.contactNumb = contactNumb;
    }

    public Company update(Company company) {
        this.type = company.getType();
        this.personIncharge = company.getPersonIncharge();
        this.position = company.getPosition();
        this.contactNumb = company.getContactNumb();
        return this;
    }

    public static Company ofRow(Row row) {
        return new Company(row.getCell(1).getStringCellValue(), row.getCell(5).getStringCellValue(),
                row.getCell(4).getStringCellValue(), row.getCell(3).getStringCellValue(), row.getCell(10).getStringCellValue());
    }
}
