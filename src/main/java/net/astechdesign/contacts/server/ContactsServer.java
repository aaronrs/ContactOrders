package net.astechdesign.contacts.server;

import net.astechdesign.contacts.repo.ContactsRepo;
import net.astechdesign.contacts.repo.DBBuilder;
import net.astechdesign.contacts.repo.TestContactsRepo;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.simple.SimpleContainerFactory;
import org.hsqldb.jdbc.JDBCDataSource;

import javax.sql.DataSource;
import javax.ws.rs.core.UriBuilder;
import java.io.Closeable;
import java.io.IOException;
import java.net.URI;

public class ContactsServer {

    public static void main(String[] args) throws IOException {
        initialiseApp();
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(8787).build();
        ResourceConfig config = new ResourceConfig();
        config.packages("net.astechdesign.contacts.resources");
        Closeable server = SimpleContainerFactory.create(baseUri, config);
        try {
            System.out.println("Press enter to stop server.");
            System.in.read();
        } finally {
            server.close();
        }
    }

    private static void initialiseApp() {
        try {
            DataSource datasource = datasource();
            new ContactsRepo(datasource);
            DBBuilder.initialiseDb(datasource);
            new TestContactsRepo(datasource).init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static DataSource datasource() {
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
