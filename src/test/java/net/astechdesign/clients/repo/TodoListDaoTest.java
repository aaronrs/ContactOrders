package net.astechdesign.clients.repo;

import org.junit.Test;

import javax.sql.DataSource;

public class TodoListDaoTest {

    @Test
    public void get_shouldReturnListOfTodos() throws Exception {
        DataSource dataSource = TestDataSource.getDataSource();
        DBBuilder.initialiseDb(dataSource);
        new TestContactsRepo();
    }
}
