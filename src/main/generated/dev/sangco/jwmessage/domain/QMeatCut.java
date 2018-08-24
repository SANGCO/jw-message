package dev.sangco.jwmessage.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QMeatCut is a Querydsl query type for MeatCut
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMeatCut extends EntityPathBase<MeatCut> {

    private static final long serialVersionUID = -458076669L;

    public static final QMeatCut meatCut = new QMeatCut("meatCut");

    public final StringPath meatCutName = createString("meatCutName");

    public QMeatCut(String variable) {
        super(MeatCut.class, forVariable(variable));
    }

    public QMeatCut(Path<? extends MeatCut> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMeatCut(PathMetadata metadata) {
        super(MeatCut.class, metadata);
    }

}

