package net.astechdesign.clients.model;

import java.util.Date;

public class Todo {

    public final int id;
    public final int contactId;
    public final String contactName;
    public final Date start;
    public final Date end;
    public final String notes;

    public Todo(int id, int contactId, String contactName, Date start, Date end, String notes) {
        this.id = id;
        this.contactId = contactId;
        this.contactName = contactName;
        this.start = start;
        this.end = end;
        this.notes = notes;
    }
}
