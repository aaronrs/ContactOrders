package net.astechdesign.clients.model.todo;

import net.astechdesign.clients.model.contact.Contact;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TodosTest {

    @Test
    public void add_shouldNormaliseTodos() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");

        Todo todo1 = new Todo(1,1, LocalDate.now(), "note1", "name1", "town1");

        Todos todos = new Todos();
        todos.add(todo1);
        todos.add(todo1);
        Map<String, List<Map<Date, List<Map<Contact, List<String>>>>>> todoMap = todos.get();

        ArrayList months = new ArrayList<>(todoMap.keySet());
        assertThat(months.size(), is(1));
//        assertThat(months.get(0), is(sdf.format(from1)));
    }
}