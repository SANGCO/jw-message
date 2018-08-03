package dev.sangco.jwmessage.domain;

import dev.sangco.jwmessage.support.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(of = "id", callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class SendResult extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String acc_id;

    @Column(nullable = false)
    private String result_code;

    @Column(nullable = false)
    private String message;

    @Column
    private String msg_id;

    @Column
    private String success_cnt;

    @Column
    private String error_cnt;

    @Column
    private String msg_type;
}

