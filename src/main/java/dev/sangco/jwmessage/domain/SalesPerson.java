package dev.sangco.jwmessage.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SalesPerson {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String salesPersonName;
}
