package net.astechdesign.clients.repo;

import org.hsqldb.jdbc.JDBCDataSource;

import javax.sql.DataSource;

public class TestDataSource {

    public static DataSource getDataSource() {
        String DB_URL = "jdbc:hsqldb:mem:Contacts";
        String USER = "SA";
        String PASSWORD = "";
        JDBCDataSource jdbcDataSource = new JDBCDataSource();
        jdbcDataSource.setUrl(DB_URL);
        jdbcDataSource.setUser(USER);
        jdbcDataSource.setPassword(PASSWORD);
        return jdbcDataSource;
    }
}
