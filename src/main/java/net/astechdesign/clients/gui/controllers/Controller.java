package net.astechdesign.clients.gui.controllers;

public abstract class Controller {

    protected MainController mainController;

    public void mainController(MainController mainController) {
        this.mainController = mainController;
    }
}
