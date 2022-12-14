package com.marketboro.point.domain.history;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHistory is a Querydsl query type for History
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHistory extends EntityPathBase<History> {

    private static final long serialVersionUID = -625614327L;

    public static final QHistory history = new QHistory("history");

    public final com.marketboro.point.domain.QBaseTimeEntity _super = new com.marketboro.point.domain.QBaseTimeEntity(this);

    public final NumberPath<java.math.BigDecimal> amount = createNumber("amount", java.math.BigDecimal.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createAt = _super.createAt;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final BooleanPath isCanceled = createBoolean("isCanceled");

    public final ListPath<com.marketboro.point.domain.reserves_history.ReservesHistory, com.marketboro.point.domain.reserves_history.QReservesHistory> reservesHistoryList = this.<com.marketboro.point.domain.reserves_history.ReservesHistory, com.marketboro.point.domain.reserves_history.QReservesHistory>createList("reservesHistoryList", com.marketboro.point.domain.reserves_history.ReservesHistory.class, com.marketboro.point.domain.reserves_history.QReservesHistory.class, PathInits.DIRECT2);

    public final EnumPath<com.marketboro.point.dto.enums.HistoryType> type = createEnum("type", com.marketboro.point.dto.enums.HistoryType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateAt = _super.updateAt;

    public QHistory(String variable) {
        super(History.class, forVariable(variable));
    }

    public QHistory(Path<? extends History> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHistory(PathMetadata metadata) {
        super(History.class, metadata);
    }

}

