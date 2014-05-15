package net.astechdesign.contacts.repo;

import net.astechdesign.contacts.model.Contact;
import net.astechdesign.contacts.model.Order;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang.StringUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class ContactsDao {
    public static final String[] CONTACT_COLUMNS = {"first","last","number","houseName","address1","town","county","postcode","telephone"};
    public static final String CONTACT_VALUES = "VALUES(?,?,?,?,?,?,?,?,?)";
    private final DataSource dataSource;

    public ContactsDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Contact> get() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(dataSource);
        ResultSetHandler<List<Contact>> contactsHandler = new BeanListHandler<>(Contact.class, new ContactRowProcessor());
        return queryRunner.query("SELECT * FROM contacts", contactsHandler);
    }

    public Contact get(int id) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(dataSource);
        ResultSetHandler<Contact> contactHandler = new BeanHandler<>(Contact.class, new ContactRowProcessor());
        return queryRunner.query("SELECT * FROM contacts WHERE id=?", contactHandler, id);
    }

    public void save(Contact contact) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(dataSource);
        if (contact.id == -1) {
            String sql = "INSERT INTO contacts (" +
                    StringUtils.join(CONTACT_COLUMNS,",") + ") " +
                    CONTACT_VALUES;
            queryRunner.update(sql,
                    contact.name.first,
                    contact.name.last,
                    contact.address.number,
                    contact.address.houseName,
                    contact.address.address1,
                    contact.address.town,
                    contact.address.county,
                    contact.address.postcode,
                    contact.address.telephone);
        } else {
            String sql = "UPDATE contacts set " +
                    StringUtils.join(CONTACT_COLUMNS, "=?,") +
                    "=? WHERE id=?";
            queryRunner.update(sql,
                    contact.name.first,
                    contact.name.last,
                    contact.address.number,
                    contact.address.houseName,
                    contact.address.address1,
                    contact.address.town,
                    contact.address.county,
                    contact.address.postcode,
                    contact.address.telephone,
                    contact.id);
        }

    }

    public void save(Order order) throws SQLException {
    }
}
