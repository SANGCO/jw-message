package dev.sangco.jwmessage.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QTypeOfBiz is a Querydsl query type for TypeOfBiz
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTypeOfBiz extends EntityPathBase<TypeOfBiz> {

    private static final long serialVersionUID = -1387018962L;

    public static final QTypeOfBiz typeOfBiz = new QTypeOfBiz("typeOfBiz");

    public final StringPath type = createString("type");

    public QTypeOfBiz(String variable) {
        super(TypeOfBiz.class, forVariable(variable));
    }

    public QTypeOfBiz(Path<? extends TypeOfBiz> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTypeOfBiz(PathMetadata metadata) {
        super(TypeOfBiz.class, metadata);
    }

}

