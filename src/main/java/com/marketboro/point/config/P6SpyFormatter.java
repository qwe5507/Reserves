package com.marketboro.point.config;

import com.p6spy.engine.common.ConnectionInformation;
import com.p6spy.engine.event.JdbcEventListener;
import com.p6spy.engine.spy.P6SpyOptions;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.sql.SQLException;

@Component  // 1
@Slf4j
public class P6SpyFormatter extends JdbcEventListener implements MessageFormattingStrategy {

    @Override // 2
    public void onAfterGetConnection(ConnectionInformation connectionInformation, SQLException e) {
        P6SpyOptions.getActiveInstance().setLogMessageFormat(getClass().getName());
    }

    @Override // 3
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        StringBuilder sb = new StringBuilder();

        sb.append(category).append(" ").append(elapsed).append("ms");
        if (StringUtils.hasText(sql)) {
            // 하이라이트가 로그 저장시에 이상한 숫자로 표시됨
//            sb.append(highlight(format(sql)));
            sb.append(format(sql));
        }
        return sb.toString();
//        return "#" + now + " | took " + elapsed + "ms | " + category + " | connection " + connectionId + "| url " + url + "\n" + prepared + "\n" + sql +";";
    }
    // 위의 주석 친 포매터가 이상한 데이터가 포함되서
    private String format(String sql) {
        if (isDDL(sql)) {
            return FormatStyle.DDL.getFormatter().format(sql);
        } else if (isBasic(sql)) {
            return FormatStyle.BASIC.getFormatter().format(sql);
        }
        return sql;
    }

    private String highlight(String sql) {
        return FormatStyle.HIGHLIGHT.getFormatter().format(sql);
    }

    private boolean isDDL(String sql) {
        return sql.startsWith("create") || sql.startsWith("alter") || sql.startsWith("comment");
    }

    private boolean isBasic(String sql) {
        return sql.startsWith("select") || sql.startsWith("insert") || sql.startsWith("update") || sql.startsWith("delete");
    }

}