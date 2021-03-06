package dev.sangco.jwmessage.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class SalesPerson {

    @Id
    @Column(unique = true, nullable = false)
    String salesPersonName;
}
