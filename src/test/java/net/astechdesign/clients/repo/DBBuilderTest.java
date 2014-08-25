package net.astechdesign.clients.repo;

import org.junit.Test;

import javax.sql.DataSource;

public class DBBuilderTest {

    @Test
    public void buildDB() throws Exception {

        DataSource dataSource = TestDataSource.getDataSource();
        DBBuilder.initialiseDb(dataSource);
        DBBuilder.initialiseDb(dataSource);

    }
}
