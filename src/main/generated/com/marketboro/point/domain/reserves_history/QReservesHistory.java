package com.marketboro.point.domain.reserves_history;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReservesHistory is a Querydsl query type for ReservesHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReservesHistory extends EntityPathBase<ReservesHistory> {

    private static final long serialVersionUID = -287934004L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReservesHistory reservesHistory = new QReservesHistory("reservesHistory");

    public final com.marketboro.point.domain.history.QHistory history;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.marketboro.point.domain.reserves.QReserves reserves;

    public QReservesHistory(String variable) {
        this(ReservesHistory.class, forVariable(variable), INITS);
    }

    public QReservesHistory(Path<? extends ReservesHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReservesHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReservesHistory(PathMetadata metadata, PathInits inits) {
        this(ReservesHistory.class, metadata, inits);
    }

    public QReservesHistory(Class<? extends ReservesHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.history = inits.isInitialized("history") ? new com.marketboro.point.domain.history.QHistory(forProperty("history")) : null;
        this.reserves = inits.isInitialized("reserves") ? new com.marketboro.point.domain.reserves.QReserves(forProperty("reserves")) : null;
    }

}

