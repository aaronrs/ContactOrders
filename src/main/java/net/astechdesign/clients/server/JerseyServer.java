package net.astechdesign.clients.server;

import net.astechdesign.clients.db.HsqlServerRunner;
import net.astechdesign.clients.model.contact.ContactRepo;
import net.astechdesign.clients.model.order.OrderRepo;
import net.astechdesign.clients.model.product.ProductRepo;
import net.astechdesign.clients.model.todo.TodoRepo;
import net.astechdesign.clients.repo.DBBuilder;
import net.astechdesign.clients.resources.ContactRequests;
import net.astechdesign.clients.resources.ProductRequests;
import net.astechdesign.clients.resources.TodoRequests;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
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
        resourceConfig.register(new DateConverterProvider());
        resourceConfig.register(new ProductRequests());
        resourceConfig.register(new ContactRequests());
        resourceConfig.register(new TodoRequests());
        resourceConfig.register(new ThymeLeafWriter());

        ServletContainer servletContainer = new ServletContainer(resourceConfig);

        ServletHolder servletHolder = new ServletHolder(servletContainer);

        ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.setContextPath("/");
        servletContextHandler.addServlet(servletHolder, "/*");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(JerseyServer.class.getResource("/static").toExternalForm());
        ContextHandler contextHandler = new ContextHandler("/static");
        contextHandler.setHandler(resourceHandler);

        HandlerCollection handlerCollection = new HandlerCollection();
        handlerCollection.setHandlers(new Handler[]{contextHandler, servletContextHandler});

        Server server = new Server(8001);
        server.setHandler(handlerCollection);

        server.start();
        server.join();
    }

    private static void initialiseApp() throws Exception {
        HsqlServerRunner.start();
        try {
            DataSource datasource = HsqlServerRunner.dataSource;
            new ProductRepo(datasource);
            new ContactRepo(datasource);
            new TodoRepo(datasource);
            new OrderRepo(datasource);
            DBBuilder.initialiseDb(datasource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static DataSource datasource() {
        String DB_URL = "jdbc:hsqldb:hsql://localhost/contacts";
        String USER = "SA";
        JDBCDataSource jdbcDataSource = new JDBCDataSource();
        jdbcDataSource.setUrl(DB_URL);
        jdbcDataSource.setUser(USER);
        return jdbcDataSource;
    }
}
