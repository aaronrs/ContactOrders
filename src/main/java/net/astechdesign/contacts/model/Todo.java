package net.astechdesign.contacts.model;

import java.util.Date;

public class Todo {

    public final int id;
    public final int contactId;
    public final Date start;
    public final Date end;
    public final String notes;

    public Todo(int id, int contactId, Date start, Date end, String notes) {
        this.id = id;
        this.contactId = contactId;
        this.start = start;
        this.end = end;
        this.notes = notes;
    }
}
