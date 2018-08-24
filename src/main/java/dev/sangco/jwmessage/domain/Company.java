package dev.sangco.jwmessage.domain;

import dev.sangco.jwmessage.support.domain.BaseTimeEntity;
import lombok.*;
import org.apache.poi.ss.usermodel.Row;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

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

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "type_of_biz")
    private TypeOfBiz typeOfBiz;

    @Column(nullable = false)
    private String personIncharge;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false)
    private String contactNumb;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_person")
    private SalesPerson salesPerson;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "COMPANY_MEATCUT",
            joinColumns = @JoinColumn(name = "COMPANY_NAME"),
            inverseJoinColumns = @JoinColumn(name = "MEAT_CUT_NAME"))
    private Set<MeatCut> meatCuts;

    public Company update(Company uCompany) {
        this.typeOfBiz = uCompany.getTypeOfBiz();
        this.personIncharge = uCompany.getPersonIncharge();
        this.position = uCompany.getPosition();
        this.contactNumb = uCompany.getContactNumb();
        this.salesPerson = uCompany.getSalesPerson();
        this.meatCuts = uCompany.getMeatCuts();
        return this;
    }
}