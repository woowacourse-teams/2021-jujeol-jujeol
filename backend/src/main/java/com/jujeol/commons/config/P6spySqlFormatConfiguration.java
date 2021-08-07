package com.jujeol.commons.config;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import java.util.Locale;
import org.hibernate.engine.jdbc.internal.FormatStyle;

public class P6spySqlFormatConfiguration implements MessageFormattingStrategy {

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category,
            String prepared, String sql, String url) {
        return formatSql(category, sql);
    }

    private String formatSql(String category, String sql) {
        if (sql == null || sql.trim().equals("")) {
            return "Transaction End!";
        }

        if (Category.STATEMENT.getName().equals(category)) {
            String tmpsql = sql.trim().toLowerCase(Locale.ROOT);
            if (tmpsql.startsWith("create")
                    || tmpsql.startsWith("alter")
                    || tmpsql.startsWith("comment")) {
                sql = FormatStyle.DDL.getFormatter().format(sql);
            } else {
                sql = FormatStyle.BASIC.getFormatter().format(sql);
            }
            sql = "Query State" + sql;
        }
        return sql;
    }
}
