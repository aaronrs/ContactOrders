package net.astechdesign.clients.repo;

import net.astechdesign.clients.model.contact.ContactDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.sql.DataSource;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContactDaoTest {

    @Mock private DataSource dataSource;

    @InjectMocks
    private ContactDao contactDao;

    @Test
    public void get_shouldReturnAContact_givenAnId() throws Exception {
        when(dataSource.getConnection()).thenReturn(null);
        contactDao.getContact(0);
    }
}
