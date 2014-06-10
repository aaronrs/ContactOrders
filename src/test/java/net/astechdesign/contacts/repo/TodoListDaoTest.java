package net.astechdesign.contacts.repo;

import net.astechdesign.contacts.model.Todo;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.Date;

public class TodoListDaoTest {

    @Test
    public void get_shouldReturnListOfTodos() throws Exception {
        DataSource dataSource = TestDataSource.getDataSource();
        DBBuilder.initialiseDb(dataSource);
        new TestContactsRepo(dataSource);
        TodoListDao todoListDao = new TodoListDao(dataSource);
        todoListDao.save(new Todo(-1,1,new Date(), new Date(), "stuff"));
        todoListDao.get();

    }
}
