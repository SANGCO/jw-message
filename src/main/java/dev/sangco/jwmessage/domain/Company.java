package dev.sangco.jwmessage.domain;

import dev.sangco.jwmessage.support.domain.BaseTimeEntity;
import lombok.*;
import org.apache.poi.ss.usermodel.Row;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(of = "companyName", callSuper = false)
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column
//    @Column(nullable = false)
    private String contactNumb;

//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinColumn(foreignKey = @ForeignKey(name = "fk_company_account"))
//    private Set<Account> accounts;
//
//    public void setAccount(Account account) {
//        if (this.accounts == null) {
//            this.accounts = new HashSet<>();
//            this.accounts.add(account);
//        }
//        this.accounts.add(account);
//    }

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
        return Company.builder()
                .companyName(row.getCell(1).getStringCellValue())
                .type(row.getCell(5).getStringCellValue())
                .personIncharge(row.getCell(4).getStringCellValue())
                .position(row.getCell(3).getStringCellValue())
                .contactNumb(row.getCell(10).getStringCellValue()).build();
   }
}