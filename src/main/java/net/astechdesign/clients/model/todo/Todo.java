package net.astechdesign.clients.model.todo;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Todo {

    private final IntegerProperty id;
    private final IntegerProperty contactId;
    private final ObjectProperty<LocalDate> date;
    private final StringProperty notes;
    private final StringProperty name;
    private final StringProperty town;

    public Todo(int id, int contactId, LocalDate date, String notes) {
        this(id, contactId, date, notes, "", "");
    }

    public Todo(int id, int contactId, LocalDate date, String notes, String name, String town) {
        this.id = new SimpleIntegerProperty(id);
        this.contactId = new SimpleIntegerProperty(contactId);
        this.date = new SimpleObjectProperty<>(date);
        this.notes = new SimpleStringProperty(notes);
        this.name = new SimpleStringProperty(name);
        this.town = new SimpleStringProperty(town);
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

    public String getTown() {
        return town.get();
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

    public void setTown(String value) {
        this.town.setValue(value);
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

    public StringProperty townProperty() {
        return town;
    }
}
