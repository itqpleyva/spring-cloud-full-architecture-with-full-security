package com.itqpleyva.springjwtsecurity.Models;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QACLModel is a Querydsl query type for ACLModel
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QACLModel extends EntityPathBase<ACLModel> {

    private static final long serialVersionUID = -1167473259L;

    public static final QACLModel aCLModel = new QACLModel("aCLModel");

    public final StringPath allowed_roles = createString("allowed_roles");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath method = createString("method");

    public final StringPath path = createString("path");

    public QACLModel(String variable) {
        super(ACLModel.class, forVariable(variable));
    }

    public QACLModel(Path<? extends ACLModel> path) {
        super(path.getType(), path.getMetadata());
    }

    public QACLModel(PathMetadata metadata) {
        super(ACLModel.class, metadata);
    }

}

