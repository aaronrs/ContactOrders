package net.astechdesign.clients.gui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.astechdesign.clients.db.HsqlServerRunner;
import net.astechdesign.clients.gui.controllers.MainController;
import net.astechdesign.clients.model.contact.ContactRepo;
import net.astechdesign.clients.model.order.OrderRepo;
import net.astechdesign.clients.model.product.ProductRepo;
import net.astechdesign.clients.model.todo.TodoRepo;
import net.astechdesign.clients.repo.DBBuilder;

import javax.sql.DataSource;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        initialiseApp();
        ClassLoader classLoader = Main.class.getClassLoader();
        FXMLLoader fxmlLoader = new FXMLLoader(classLoader.getResource("fxml/main.fxml"));
        VBox main = fxmlLoader.load();
        MainController mainController = fxmlLoader.getController();

        FXMLLoader productsLoader = new FXMLLoader(classLoader.getResource("fxml/products.fxml"));
        AnchorPane productsPane = productsLoader.load();
        mainController.productsPane = productsPane;
        mainController.showProducts();

        FXMLLoader contactsLoader = new FXMLLoader(classLoader.getResource("fxml/contacts.fxml"));
        AnchorPane contactsPane = contactsLoader.load();
        mainController.contactsPane = contactsPane;
        mainController.showContacts();

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

}
