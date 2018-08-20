package dev.sangco.jwmessage.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCompany is a Querydsl query type for Company
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCompany extends EntityPathBase<Company> {

    private static final long serialVersionUID = -445895671L;

    public static final QCompany company = new QCompany("company");

    public final dev.sangco.jwmessage.support.domain.QBaseTimeEntity _super = new dev.sangco.jwmessage.support.domain.QBaseTimeEntity(this);

    public final StringPath companyName = createString("companyName");

    public final StringPath contactNumb = createString("contactNumb");

    //inherited
    public final DateTimePath<java.sql.Timestamp> createDate = _super.createDate;

    public final StringPath meatCuts = createString("meatCuts");

    //inherited
    public final DateTimePath<java.sql.Timestamp> modifiedDate = _super.modifiedDate;

    public final StringPath personIncharge = createString("personIncharge");

    public final StringPath position = createString("position");

    public final StringPath salesPerson = createString("salesPerson");

    public final StringPath type = createString("type");

    public QCompany(String variable) {
        super(Company.class, forVariable(variable));
    }

    public QCompany(Path<? extends Company> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCompany(PathMetadata metadata) {
        super(Company.class, metadata);
    }

}

