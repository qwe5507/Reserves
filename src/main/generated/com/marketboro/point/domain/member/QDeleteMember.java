package com.marketboro.point.domain.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDeleteMember is a Querydsl query type for DeleteMember
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDeleteMember extends EntityPathBase<DeleteMember> {

    private static final long serialVersionUID = -1349483384L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDeleteMember deleteMember = new QDeleteMember("deleteMember");

    public final com.marketboro.point.domain.QBaseTimeEntity _super = new com.marketboro.point.domain.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createAt = _super.createAt;

    public final QEmail email;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath name = createString("name");

    public final NumberPath<Long> previousId = createNumber("previousId", Long.class);

    public final StringPath profileUrl = createString("profileUrl");

    public final EnumPath<MemberProvider> provider = createEnum("provider", MemberProvider.class);

    public final StringPath uid = createString("uid");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateAt = _super.updateAt;

    public QDeleteMember(String variable) {
        this(DeleteMember.class, forVariable(variable), INITS);
    }

    public QDeleteMember(Path<? extends DeleteMember> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDeleteMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDeleteMember(PathMetadata metadata, PathInits inits) {
        this(DeleteMember.class, metadata, inits);
    }

    public QDeleteMember(Class<? extends DeleteMember> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.email = inits.isInitialized("email") ? new QEmail(forProperty("email")) : null;
    }

}

