package net.astechdesign.clients.model.todo;

import java.util.Date;

public class Todo {

    public final int id;
    public final int contactId;
    public final Date date;
    public final String notes;
    public final String contactName;

    public Todo(int id, int contactId, Date date, String notes) {
        this.id = id;
        this.contactId = contactId;
        this.date = date;
        this.notes = notes;
        this.contactName = "";
    }

    public Todo(int id, int contactId, Date date, String notes, String first, String last) {
        this.id = id;
        this.contactId = contactId;
        this.date = date;
        this.notes = notes;
        this.contactName = first + " " + last;
    }
}
