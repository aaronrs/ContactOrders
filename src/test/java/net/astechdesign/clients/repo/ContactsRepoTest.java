package net.astechdesign.clients.repo;

import net.astechdesign.clients.model.Todo;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ContactsRepoTest {

    public static final String NOTES = "stuff";
    public static final String NOTES2 = "more stuff";

    @Test
    public void get_shouldReturnAListOfNotes() throws Exception {
        DataSource dataSource = TestDataSource.getDataSource();

        DBBuilder.initialiseDb(dataSource);
        new TestContactsRepo(dataSource).init();

        new ContactsRepo(dataSource);

        ContactsRepo.save(new Todo(-1, 1, "name", new Date(), new Date(), "stuff"));
        ContactsRepo.save(new Todo(-1, 2, "name2", new Date(), new Date(), "more stuff"));
        List<Todo> todoList = ContactsRepo.getTodoList();
        assertThat(todoList.size(), is(2));
        assertThat(todoList.get(0).notes, is(NOTES));

        todoList = ContactsRepo.getTodoList(2);
        assertThat(todoList.size(), is(1));
        assertThat(todoList.get(0).notes, is(NOTES2));
    }
}
