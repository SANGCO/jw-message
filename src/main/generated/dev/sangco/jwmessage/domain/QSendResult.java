package dev.sangco.jwmessage.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSendResult is a Querydsl query type for SendResult
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSendResult extends EntityPathBase<SendResult> {

    private static final long serialVersionUID = 1946227385L;

    public static final QSendResult sendResult = new QSendResult("sendResult");

    public final dev.sangco.jwmessage.support.domain.QBaseTimeEntity _super = new dev.sangco.jwmessage.support.domain.QBaseTimeEntity(this);

    public final StringPath acc_id = createString("acc_id");

    //inherited
    public final DateTimePath<java.sql.Timestamp> createDate = _super.createDate;

    public final StringPath error_cnt = createString("error_cnt");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath message = createString("message");

    //inherited
    public final DateTimePath<java.sql.Timestamp> modifiedDate = _super.modifiedDate;

    public final StringPath msg_id = createString("msg_id");

    public final StringPath msg_type = createString("msg_type");

    public final StringPath result_code = createString("result_code");

    public final StringPath success_cnt = createString("success_cnt");

    public QSendResult(String variable) {
        super(SendResult.class, forVariable(variable));
    }

    public QSendResult(Path<? extends SendResult> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSendResult(PathMetadata metadata) {
        super(SendResult.class, metadata);
    }

}

