package net.astechdesign.clients.gui.fxml;

import javafx.fxml.FXMLLoader;
import net.astechdesign.clients.gui.controllers.Controller;
import net.astechdesign.clients.gui.controllers.MainController;

import java.io.IOException;

public class FmxlWrapper<T extends Controller, V> {

    private final V pane;
    private final T controller;

    public FmxlWrapper(ClassLoader classLoader, String fxml, MainController mainController) {
        FXMLLoader mainLoader = new FXMLLoader(classLoader.getResource(fxml));
        try {
            this.pane = mainLoader.load();
            controller = mainLoader.getController();
            controller.mainController(mainController);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public T getController() {
        return controller;
    }

    public V getPane() {
        return pane;
    }
}
