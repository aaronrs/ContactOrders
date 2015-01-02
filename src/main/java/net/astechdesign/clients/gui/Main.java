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

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;

public class Main extends Application {

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

        primaryStage.setScene(new Scene(initPane, initPane.getPrefWidth(), initPane.getPrefHeight()));
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
            new ProductRepo(datasource);
            new ContactRepo(datasource);
            new TodoRepo(datasource);
            new OrderRepo(datasource);
            DBBuilder.initialiseDb(datasource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
