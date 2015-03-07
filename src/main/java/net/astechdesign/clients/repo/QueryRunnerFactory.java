package net.astechdesign.clients.repo;

import org.apache.commons.dbutils.QueryRunner;

import javax.sql.DataSource;

public class QueryRunnerFactory {
    private DataSource dataSource;

    public QueryRunnerFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public QueryRunner getRunner() {
        return new QueryRunner(dataSource);
    }
}
