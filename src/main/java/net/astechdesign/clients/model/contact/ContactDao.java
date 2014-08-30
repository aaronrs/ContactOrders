package net.astechdesign.clients.model.contact;

import net.astechdesign.clients.repo.Dao;
import org.apache.commons.lang.StringUtils;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ContactDao extends Dao<Contact> {
    public static final String[] CONTACT_COLUMNS = {"first","last","number","houseName","address1","town","county","postcode","telephone"};

    public ContactDao(DataSource dataSource) {
        super(dataSource);
    }

    public List<Contact> getContacts() throws SQLException {
        String sql = "SELECT * FROM contacts";
        return listQuery(sql, Contact.class);
    }

    public Contact getContact(int id) throws SQLException {
        String sql = "SELECT * FROM contacts WHERE id=?";
        return query(sql, Contact.class, id);
    }

    public void update(Contact contact) throws SQLException {
        String sql = "UPDATE contacts set " +
                StringUtils.join(CONTACT_COLUMNS, "=?,") +
                "=? WHERE id=?";
        Object[] values = {contact.name.first,
                contact.name.last,
                contact.address.number,
                contact.address.houseName,
                contact.address.address1,
                contact.address.town,
                contact.address.county,
                contact.address.postcode,
                contact.telephone,
                contact.id
        };
        update(sql, values);
    }

    public void save(Contact contact) throws SQLException {
        String sql = "INSERT INTO contacts (" +
                StringUtils.join(CONTACT_COLUMNS,",") + ") " +
                "VALUES (?" +
                StringUtils.repeat(",?", CONTACT_COLUMNS.length - 1) +
                ")";
        Object[] values = {contact.name.first,
                contact.name.last,
                contact.address.number,
                contact.address.houseName,
                contact.address.address1,
                contact.address.town,
                contact.address.county,
                contact.address.postcode,
                contact.telephone.number
        };
        update(sql, values);
    }


    @Override
    public <T> T toBean(ResultSet rs, Class<T> type) throws SQLException {
        Name name = new Name(rs.getString("first"), rs.getString("last"));
        Address address = new Address(
                rs.getInt("number"),
                rs.getString("houseName"),
                rs.getString("address1"),
                rs.getString("town"),
                rs.getString("county"),
                rs.getString("postcode")
        );
        return (T)new Contact(rs.getInt("id"), name, address, new Telephone(rs.getString("telephone")));
    }

    public Contact find(String name) throws SQLException {
        String sql = "SELECT * FROM contacts WHERE first like '%?%' or last like '%?%'";
        return query(sql, Contact.class, name, name);
    }
}
