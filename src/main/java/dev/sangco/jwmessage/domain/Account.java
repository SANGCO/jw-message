package dev.sangco.jwmessage.domain;

import dev.sangco.jwmessage.support.domain.BaseTimeEntity;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.List;

@Builder
@EqualsAndHashCode(of = "accId", callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
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

    public Account update(AccountDto.Update uAccount) {
        this.setPassword(uAccount.getPassword());
        this.setName(uAccount.getName());
        this.setPhoneNumb(uAccount.getPhoneNumb());
        this.setAligoId(uAccount.getAligoId());
        this.setAligoKey(uAccount.getAligoId());
        return this;
    }
}
