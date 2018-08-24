package dev.sangco.jwmessage.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCompany is a Querydsl query type for Company
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCompany extends EntityPathBase<Company> {

    private static final long serialVersionUID = -445895671L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCompany company = new QCompany("company");

    public final dev.sangco.jwmessage.support.domain.QBaseTimeEntity _super = new dev.sangco.jwmessage.support.domain.QBaseTimeEntity(this);

    public final StringPath companyName = createString("companyName");

    public final StringPath contactNumb = createString("contactNumb");

    //inherited
    public final DateTimePath<java.sql.Timestamp> createDate = _super.createDate;

    public final SetPath<MeatCut, QMeatCut> meatCuts = this.<MeatCut, QMeatCut>createSet("meatCuts", MeatCut.class, QMeatCut.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.sql.Timestamp> modifiedDate = _super.modifiedDate;

    public final StringPath personIncharge = createString("personIncharge");

    public final StringPath position = createString("position");

    public final QSalesPerson salesPerson;

    public final QTypeOfBiz typeOfBiz;

    public QCompany(String variable) {
        this(Company.class, forVariable(variable), INITS);
    }

    public QCompany(Path<? extends Company> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCompany(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCompany(PathMetadata metadata, PathInits inits) {
        this(Company.class, metadata, inits);
    }

    public QCompany(Class<? extends Company> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.salesPerson = inits.isInitialized("salesPerson") ? new QSalesPerson(forProperty("salesPerson")) : null;
        this.typeOfBiz = inits.isInitialized("typeOfBiz") ? new QTypeOfBiz(forProperty("typeOfBiz")) : null;
    }

}

