package net.astechdesign.clients.db;

import org.hsqldb.jdbc.JDBCDataSource;
import org.hsqldb.persist.HsqlProperties;
import org.hsqldb.server.Server;
import org.hsqldb.server.ServerAcl;

import java.io.IOException;
import java.util.Properties;

public class HsqlServerRunner implements Runnable {

    public static JDBCDataSource dataSource;
    public static Properties config;

    public static void main(String[] args) throws Exception {
        start();
    }

    public static void start() throws Exception {
        new Thread(new HsqlServerRunner()).start();

        dataSource = new JDBCDataSource();
        dataSource.setUrl(config.getProperty("dataSource.url"));
        dataSource.setUser(config.getProperty("dataSource.user"));
    }

    @Override
    public void run() {
        Server server = new Server();
        Properties props = new Properties();
        props.setProperty("server.database.0", config.getProperty("server.database"));
        props.setProperty("server.dbname.0", config.getProperty("server.dbname"));
        try {
            server.setProperties(new HsqlProperties(props));
        } catch (IOException | ServerAcl.AclFormatException e) {
            e.printStackTrace();
        }
        server.start();
    }
}