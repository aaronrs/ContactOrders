package net.astechdesign.clients.gui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.astechdesign.clients.db.HsqlServerRunner;
import net.astechdesign.clients.gui.controllers.MainController;
import net.astechdesign.clients.gui.fxml.FmxlWrapper;
import net.astechdesign.clients.model.contact.ContactRepo;
import net.astechdesign.clients.model.order.OrderRepo;
import net.astechdesign.clients.model.product.ProductRepo;
import net.astechdesign.clients.model.todo.TodoRepo;
import net.astechdesign.clients.repo.DBBuilder;
import net.astechdesign.clients.repo.QueryRunnerFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;

public class Main extends Application {

    // Screen size - 1366 x 768
    public static int height = 730;
    public static int width = 1300;

    // Close windows on exit
    //// Todos need sorting by date
    // add single delivery note
    // Split dates across columns
    // Fix date format on Orders etc
    // new contact - should go to details tab
    // Indicate updated/saved fields when clicked
    // Do not save multiple times
    // edit products - desc and price
    // backup database

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        InputStream is = Main.class.getClassLoader().getResourceAsStream("config.properties");
        Properties config = new Properties();
        config.load(is);

        ClassLoader classLoader = Main.class.getClassLoader();
        FXMLLoader initLoader = new FXMLLoader(classLoader.getResource("fxml/init.fxml"));
        VBox initPane = initLoader.load();

        primaryStage.setScene(new Scene(initPane, width, height));
        primaryStage.setTitle("Order Management");
        primaryStage.show();

        initialiseApp(config);

        FmxlWrapper<MainController, VBox> mainWrapper = new FmxlWrapper(classLoader, "fxml/main.fxml", null);

        VBox main = mainWrapper.getPane();

        primaryStage.setScene(new Scene(main, main.getPrefWidth(), main.getPrefHeight()));
        primaryStage.setTitle("Order Management");
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
    }

    private static void initialiseApp(Properties config) throws Exception {
        HsqlServerRunner.config = config;
        HsqlServerRunner.start();
        try {
            DataSource datasource = HsqlServerRunner.dataSource;
            new ProductRepo(new QueryRunnerFactory(datasource));
            new ContactRepo(datasource);
            new TodoRepo(datasource);
            new OrderRepo(datasource);
            DBBuilder.initialiseDb(datasource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
