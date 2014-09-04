package net.astechdesign.clients.db;

import org.hsqldb.jdbc.JDBCDataSource;
import org.hsqldb.persist.HsqlProperties;
import org.hsqldb.server.Server;
import org.hsqldb.server.ServerAcl;

import java.io.IOException;
import java.util.Properties;

public class HsqlServerRunner implements Runnable {

    public static JDBCDataSource dataSource;

    public static void main(String[] args) throws Exception {
        start();
    }

    public static void start() throws Exception {
        new Thread(new HsqlServerRunner()).start();

        dataSource = new JDBCDataSource();
        dataSource.setUrl("jdbc:hsqldb:hsql://localhost/Contacts");
        dataSource.setUser("SA");
    }

    @Override
    public void run() {
        Server server = new Server();
        Properties props = new Properties();
        props.setProperty("server.database.0", "file:/home/aaron/db/contacts");
        props.setProperty("server.dbname.0", "contacts");
        try {
            server.setProperties(new HsqlProperties(props));
        } catch (IOException | ServerAcl.AclFormatException e) {
            e.printStackTrace();
        }
        server.start();
    }
}