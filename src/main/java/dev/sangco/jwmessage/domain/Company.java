package dev.sangco.jwmessage.domain;

import dev.sangco.jwmessage.support.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
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

    private String personIncharge;

    private String position;

    @Column(nullable = false)
    private String contactNumb;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_person", nullable = false)
    private SalesPerson salesPerson;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "COMPANY_MEATCUT",
            joinColumns = @JoinColumn(name = "COMPANY_NAME"),
            inverseJoinColumns = @JoinColumn(name = "MEAT_CUT_NAME"))
    private Set<MeatCut> meatCuts;
}