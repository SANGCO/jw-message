package dev.sangco.jwmessage.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAccount is a Querydsl query type for Account
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAccount extends EntityPathBase<Account> {

    private static final long serialVersionUID = 1721268665L;

    public static final QAccount account = new QAccount("account");

    public final dev.sangco.jwmessage.support.domain.QBaseTimeEntity _super = new dev.sangco.jwmessage.support.domain.QBaseTimeEntity(this);

    public final StringPath accId = createString("accId");

    public final StringPath aligoId = createString("aligoId");

    public final StringPath aligoKey = createString("aligoKey");

    //inherited
    public final DateTimePath<java.sql.Timestamp> createDate = _super.createDate;

    //inherited
    public final DateTimePath<java.sql.Timestamp> modifiedDate = _super.modifiedDate;

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final StringPath phoneNumb = createString("phoneNumb");

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public QAccount(String variable) {
        super(Account.class, forVariable(variable));
    }

    public QAccount(Path<? extends Account> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAccount(PathMetadata metadata) {
        super(Account.class, metadata);
    }

}

