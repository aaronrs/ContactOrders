package net.astechdesign.clients.gui.controllers;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateCellFactory<T> implements Callback<TableColumn<T, LocalDate>, TableCell<T, LocalDate>> {
    @Override
    public TableCell<T, LocalDate> call(TableColumn<T, LocalDate> param) {
        return new TableCell<T, LocalDate>() {

            private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");

            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                if (!empty && item != null) {
                    this.setText(item.format(formatter));
                }
            }
        };
    }
}
