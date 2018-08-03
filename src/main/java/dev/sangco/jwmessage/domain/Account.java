package dev.sangco.jwmessage.domain;

import dev.sangco.jwmessage.support.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(of = "accId", callSuper = false)
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class Account extends BaseTimeEntity {

    @Id
    @Column(unique = true, nullable = false, length = 20)
    private String accId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false)
    private String phoneNumb;

    @Column(nullable = false)
    private String aligoId;

    @Column(nullable = false)
    private String aligoKey;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Account(String accId, String password, String name, String phoneNumb, String aligoId, String aligoKey, Role role) {
        this.accId = accId;
        this.password = password;
        this.name = name;
        this.phoneNumb = phoneNumb;
        this.aligoId = aligoId;
        this.aligoKey = aligoKey;
        this.role = role;
    }
}
