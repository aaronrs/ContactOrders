package net.astechdesign.clients.model.todo;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Todo {

    private final IntegerProperty id;
    private final IntegerProperty contactId;
    private final ObjectProperty<LocalDate> date;
    private final StringProperty notes;
    private final StringProperty name;

    public Todo(int id, int contactId, LocalDate date, String notes, String name) {
        this.id = new SimpleIntegerProperty(id);
        this.contactId = new SimpleIntegerProperty(contactId);
        this.date = new SimpleObjectProperty<LocalDate>(date);
        this.notes = new SimpleStringProperty(notes);
        this.name = new SimpleStringProperty(name);
    }

    public int getId() {
        return id.get();
    }

    public int getContactId() {
        return contactId.get();
    }

    public LocalDate getDate() {
        return date.get();
    }

    public String getNotes() {
        return notes.get();
    }

    public String getName() {
        return name.get();
    }

    public void setId(int id) {
        this.id.setValue(id);
    }

    public void setContactId(int contactId) {
        this.contactId.setValue(contactId);
    }

    public void setDate(LocalDate date) {
        this.date.setValue(date);
    }

    public void setNotes(String value) {
        this.notes.setValue(value);
    }

    public void setName(String value) {
        this.name.setValue(value);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public IntegerProperty contactIdProperty() {
        return contactId;
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public StringProperty notesProperty() {
        return notes;
    }

    public StringProperty nameProperty() {
        return name;
    }
}
