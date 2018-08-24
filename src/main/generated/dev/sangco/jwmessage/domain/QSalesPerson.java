package dev.sangco.jwmessage.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSalesPerson is a Querydsl query type for SalesPerson
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSalesPerson extends EntityPathBase<SalesPerson> {

    private static final long serialVersionUID = 660939021L;

    public static final QSalesPerson salesPerson = new QSalesPerson("salesPerson");

    public final StringPath salesPersonName = createString("salesPersonName");

    public QSalesPerson(String variable) {
        super(SalesPerson.class, forVariable(variable));
    }

    public QSalesPerson(Path<? extends SalesPerson> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSalesPerson(PathMetadata metadata) {
        super(SalesPerson.class, metadata);
    }

}

