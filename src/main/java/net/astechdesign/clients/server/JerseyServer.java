package net.astechdesign.clients.server;

import net.astechdesign.clients.repo.ContactsRepo;
import net.astechdesign.clients.repo.DBBuilder;
import net.astechdesign.clients.repo.TestContactsRepo;
import net.astechdesign.clients.resources.ContactsResource;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.hsqldb.jdbc.JDBCDataSource;

import javax.sql.DataSource;

public class JerseyServer {

    public static void main(String[] args) throws Exception {
        initialiseApp();

        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.register(new ContactsResource());
        resourceConfig.register(new ThymeLeafWriter());

        ServletContainer servletContainer = new ServletContainer(resourceConfig);

        ServletHolder servletHolder = new ServletHolder(servletContainer);

        ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.setContextPath("/");
        servletContextHandler.addServlet(servletHolder, "/*");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(JerseyServer.class.getResource("/static").toExternalForm());

        HandlerCollection handlerCollection = new HandlerCollection();
        handlerCollection.setHandlers(new Handler[]{resourceHandler, servletContextHandler});

        Server server = new Server(8000);
        server.setHandler(handlerCollection);

        server.start();
        server.join();
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
