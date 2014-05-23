package net.astechdesign.contacts.repo;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang.StringUtils;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Dao<T> extends BasicRowProcessor {
    private final DataSource dataSource;

    public Dao(DataSource dataSource) {
        this.dataSource = dataSource;
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
        return queryRunner().query(sql, handler, values);
    }

    protected List<T> listQuery(String sql, Class<T> clazz, Object... values) throws SQLException {
        ResultSetHandler<List<T>> handler = new BeanListHandler<>(clazz, this);
        return queryRunner().query(sql, handler, values);
    }

    protected void save(String table, Map<String, Object> dataMap) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder("INSERT INTO ");
        stringBuilder.append(table);
        stringBuilder.append(" (");
        stringBuilder.append(StringUtils.join(dataMap.keySet(),","));
        stringBuilder.append(") VALUES (");
        stringBuilder.append(StringUtils.repeat("?,", dataMap.size() -1));
        stringBuilder.append("?)");

        queryRunner().update(stringBuilder.toString(), dataMap.values().toArray());
    }

    protected void update(String sql, Object... values) throws SQLException {
        queryRunner().update(sql, values);
    }

    private QueryRunner queryRunner() {
        return new QueryRunner(dataSource);
    }
}
