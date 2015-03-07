package net.astechdesign.clients.model.product;

import net.astechdesign.clients.repo.QueryRunnerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ProductDaoTest {

    @Mock
    private QueryRunnerFactory factory;

    @Test
    public void testName() throws Exception {
        ProductDao productDao = new ProductDao(factory);
        Product product = productDao.find(1);
        assertThat(product.getId(), is(1));
    }
}