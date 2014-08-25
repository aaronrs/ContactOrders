package net.astechdesign.clients.server;

import net.astechdesign.clients.repo.ContactsRepo;
import net.astechdesign.clients.repo.DBBuilder;
import net.astechdesign.clients.repo.TestContactsRepo;
import net.astechdesign.clients.resources.ContactsResource;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.hsqldb.jdbc.JDBCDataSource;

import javax.sql.DataSource;
//import javax.xml.ws.handler.Handler;


public class ContactsServer {

    public static void main(String[] args) throws Exception {
//        initialiseApp();
        ResourceConfig config = new ResourceConfig();
        config.register(new ContactsResource());
//        config.packages("net.astechdesign.contacts.resources");
        config.register(new ThymeLeafWriter());

        ServletContainer servletContainer = new ServletContainer(config);
        ServletHolder sh = new ServletHolder(servletContainer);

        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.setContextPath("/");
        handler.addServlet(sh, "/*");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(ContactsServer.class.getResource("/static").toExternalForm());
        ContextHandler staticContent = new ContextHandler("/static");
        staticContent.setHandler(resourceHandler);
        ContextHandlerCollection handlerList = new ContextHandlerCollection();
        handlerList.setHandlers(new ContextHandler[] {staticContent, handler});

        Server server = new Server(8787);
        server.setHandler(handlerList);

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
