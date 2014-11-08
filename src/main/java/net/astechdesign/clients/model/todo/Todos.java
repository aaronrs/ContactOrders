package net.astechdesign.clients.model.todo;

import net.astechdesign.clients.model.contact.Contact;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Todos {

    private Map<String, List<Map<Date, List<Map<Contact, List<String>>>>>> todoMap = new TreeMap<>();
    private SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");

    public void add(Todo todo) {
        todoMap.put(sdf.format(todo.date), null);
    }

    public Map<String, List<Map<Date, List<Map<Contact, List<String>>>>>> get() {
        return todoMap;
    }
}
