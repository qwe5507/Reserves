package com.marketboro.point.domain.reserves;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReserves is a Querydsl query type for Reserves
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReserves extends EntityPathBase<Reserves> {

    private static final long serialVersionUID = 1045940573L;

    public static final QReserves reserves = new QReserves("reserves");

    public final com.marketboro.point.domain.QBaseTimeEntity _super = new com.marketboro.point.domain.QBaseTimeEntity(this);

    public final NumberPath<Long> amount = createNumber("amount", Long.class);

    public final NumberPath<Long> balance = createNumber("balance", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createAt = _super.createAt;

    public final NumberPath<Long> expiredAt = createNumber("expiredAt", Long.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath memberId = createString("memberId");

    public final ListPath<com.marketboro.point.domain.reserves_history.ReservesHistory, com.marketboro.point.domain.reserves_history.QReservesHistory> reservesHistoryList = this.<com.marketboro.point.domain.reserves_history.ReservesHistory, com.marketboro.point.domain.reserves_history.QReservesHistory>createList("reservesHistoryList", com.marketboro.point.domain.reserves_history.ReservesHistory.class, com.marketboro.point.domain.reserves_history.QReservesHistory.class, PathInits.DIRECT2);

    public final EnumPath<com.marketboro.point.dto.enums.ReservesStatus> status = createEnum("status", com.marketboro.point.dto.enums.ReservesStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateAt = _super.updateAt;

    public QReserves(String variable) {
        super(Reserves.class, forVariable(variable));
    }

    public QReserves(Path<? extends Reserves> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReserves(PathMetadata metadata) {
        super(Reserves.class, metadata);
    }

}

