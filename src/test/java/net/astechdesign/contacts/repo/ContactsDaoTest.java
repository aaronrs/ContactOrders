package net.astechdesign.contacts.repo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.sql.DataSource;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContactsDaoTest {

    @Mock private DataSource dataSource;

    @InjectMocks
    private ContactsDao contactsDao;

    @Test
    public void get_shouldReturnAContact_givenAnId() throws Exception {
        when(dataSource.getConnection()).thenReturn(null);
        contactsDao.getContact(0);
    }
}
