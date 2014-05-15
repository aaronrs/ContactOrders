package net.astechdesign.contacts.repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private final Connection conn;

    public Database(String dbUrl, String user, String password) {
        try {
            this.conn = DriverManager.getConnection(dbUrl, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("", e);
        }
    }

    public Connection getConnection() {
        return conn;
    }
}