package net.astechdesign.clients.repo;

import net.astechdesign.clients.model.contact.ContactRepo;
import net.astechdesign.clients.model.todo.Todo;
import net.astechdesign.clients.model.todo.TodoRepo;
import org.junit.Test;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ContactRepoTest {

    public static final String NOTES = "stuff";
    public static final String NOTES2 = "more stuff";

    @Test
    public void get_shouldReturnAListOfNotes() throws Exception {
        DataSource dataSource = TestDataSource.getDataSource();

        DBBuilder.initialiseDb(dataSource);
        new TestContactsRepo().init();

        new ContactRepo(dataSource);

        TodoRepo.save(new Todo(-1, 1, LocalDate.now(), "stuff", ""));
        TodoRepo.save(new Todo(-1, 2, LocalDate.now(), "more stuff", ""));
        List<Todo> todoList = TodoRepo.todos();
        assertThat(todoList.size(), is(2));
        assertThat(todoList.get(0).notes, is(NOTES));

        todoList = TodoRepo.todos();
        assertThat(todoList.size(), is(1));
        assertThat(todoList.get(0).notes, is(NOTES2));
    }
}
