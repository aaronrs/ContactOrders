package net.astechdesign.clients.repo;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract class Dao<T> extends BasicRowProcessor {

    private QueryRunnerFactory qrf;

    public Dao(QueryRunnerFactory qrf) {
        this.qrf = qrf;
    }

    @Override
    public <O> List<O> toBeanList(ResultSet rs, Class<O> type) throws SQLException {
        List<O> list = new ArrayList<>();
        while (rs.next()) {
            list.add(toBean(rs, type));
        }
        return list;
    }

    protected T query(String sql, Class<T> clazz, Object... values) throws SQLException {
        ResultSetHandler<T> handler = new BeanHandler<>(clazz, this);
        return queryRunner().query(sql, handler, convertDates(values));
    }

    protected List<T> listQuery(String sql, Class<T> clazz, Object... values) throws SQLException {
        ResultSetHandler<List<T>> handler = new BeanListHandler<>(clazz, this);
        return queryRunner().query(sql, handler, convertDates(values));
    }

    protected void save(String table, Map<String, Object> dataMap) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder("INSERT INTO ");
        stringBuilder.append(table);
        stringBuilder.append(" (");
        stringBuilder.append(StringUtils.join(dataMap.keySet(),","));
        stringBuilder.append(") VALUES (");
        stringBuilder.append(StringUtils.repeat("?,", dataMap.size() -1));
        stringBuilder.append("?)");

        queryRunner().update(stringBuilder.toString(), convertDates(dataMap.values().toArray()));
    }

    protected void replace(String table, Map<String, Object> dataMap, Map<String, Object> keyMap) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder("UPDATE ");
        stringBuilder.append(table);
        stringBuilder.append(" SET ");
        List<String> values = new ArrayList<>();
        for (Map.Entry entry : dataMap.entrySet()) {
            values.add(entry.getKey() + "=" + "'" + entry.getValue() + "'");
        }
        stringBuilder.append(StringUtils.join(values, ","));
        stringBuilder.append(" where ");
        int count = 0;
        for (Map.Entry entry : keyMap.entrySet()) {
            if (count > 0) {
                stringBuilder.append(" AND ");
            }
            stringBuilder.append(entry.getKey() + "=" + entry.getValue());
            count++;
        }
        queryRunner().update(stringBuilder.toString());
    }

    protected void update(String sql, Object... values) throws SQLException {
        queryRunner().update(sql, convertDates(values));
    }

    private Object[] convertDates(Object[] values) {
        for (int i=0; i < values.length; i++) {
            if (values[i] instanceof LocalDate) {
                values[i] = convert((LocalDate)values[i]);
            }
        }
        return values;
    }

    private QueryRunner queryRunner() {
        return qrf.getRunner();
    }

    private Date convert(LocalDate date) {
        Instant instant = date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }
}
