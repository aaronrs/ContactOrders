package net.astechdesign.clients.db;


import net.astechdesign.clients.repo.DBBuilder;
import org.hsqldb.jdbc.JDBCDataSource;
import org.hsqldb.persist.HsqlProperties;
import org.hsqldb.server.Server;
import org.hsqldb.server.ServerAcl;

import java.io.IOException;
import java.util.Properties;

public class HsqlServerRunner implements Runnable {

    public static void main(String[] args) throws Exception {
        new Thread(new HsqlServerRunner()).start();
        init();
    }

    private static void init() {
        String DB_URL = "http://localhost:9001/Contacts";
        String USER = "SA";
        String PASSWORD = "";
        JDBCDataSource jdbcDataSource = new JDBCDataSource();
        jdbcDataSource.setUrl(DB_URL);
        jdbcDataSource.setUser(USER);
        jdbcDataSource.setPassword(PASSWORD);

        DBBuilder.initialiseDb(jdbcDataSource);
    }

    @Override
    public void run() {
        Server server = new Server();
        Properties props = new Properties();
        props.setProperty("server.database.0", "mem:Contacts");
        props.setProperty("server.dbname.0", "Contacts");
        try {
            server.setProperties(new HsqlProperties(props));
        } catch (IOException | ServerAcl.AclFormatException e) {
            e.printStackTrace();
        }
        server.start();
    }
}