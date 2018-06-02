package dev.sangco.jwmessage.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@ToString
@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
public class Account {
    @Id
    @GeneratedValue
    private Long id;

    @CreatedDate
    private LocalDateTime createDate;

    @Column(unique = true, nullable = false, length = 20)
    private String accountId;

    @Column(nullable = false, length = 20)
    private String password;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false)
    private String phoneNumb;

    @Column(nullable = false)
    private String aligoId;

    @Column(nullable = false)
    private String aligoKey;

    @Column(nullable = false)
    private Role role;

    @Builder
    public Account(String accountId, String password, String name, String phoneNumb, String aligoId, String aligoKey) {
        this.createDate = createDate;
        this.accountId = accountId;
        this.password = password;
        this.name = name;
        this.phoneNumb = phoneNumb;
        this.aligoId = aligoId;
        this.aligoKey = aligoKey;
    }

// * ModelMapper가 Long id 필드의 setter를 인식 못하게 롬복 안쓰고 setter 구현

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumb(String phoneNumb) {
        this.phoneNumb = phoneNumb;
    }

    public void setAligoId(String aligoId) {
        this.aligoId = aligoId;
    }

    public void setAligoKey(String aligoKey) {
        this.aligoKey = aligoKey;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
