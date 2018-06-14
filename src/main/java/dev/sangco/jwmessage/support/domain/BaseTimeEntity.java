package dev.sangco.jwmessage.support.domain;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

@Getter
@MappedSuperclass
public class BaseTimeEntity {

// TODO @CreatedDate, @LastModifiedDate mysql에 적용이 안되고 있다.
//    @CreatedDate
//    private LocalDateTime createDate;
//
//    @LastModifiedDate
//    private LocalDateTime modifiedDate;

    @CreationTimestamp
    private Timestamp createDate;

    @UpdateTimestamp
    private Timestamp modifiedDate;
}
