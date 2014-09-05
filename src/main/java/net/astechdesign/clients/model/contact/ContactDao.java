package net.astechdesign.clients.model.contact;

import net.astechdesign.clients.repo.Dao;
import org.apache.commons.lang.StringUtils;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ContactDao extends Dao<Contact> {
    public static final String[] CONTACT_COLUMNS = {"first","last","address","postcode","telephone"};

    public ContactDao(DataSource dataSource) {
        super(dataSource);
    }

    public List<Contact> getContacts() throws SQLException {
        String sql = "SELECT * FROM contacts";
        return listQuery(sql, Contact.class);
    }

    public List<Contact> find(String first, String last, String address, String postcode, String tel) throws SQLException {
        Map<String, String> map = new LinkedHashMap<>();
        if (first.trim().length() > 0) {
            map.put("lower(first)", search(first));
        }
        if (last.trim().length() > 0) {
            map.put("lower(last)", search(last));
        }
        if (address.trim().length() > 0) {
            map.put("lower(address)", search(address));
        }
        if (postcode.trim().length() > 0) {
            map.put("lower(postcode)", search(postcode));
        }
        if (tel.trim().length() > 0) {
            map.put("lower(telephone)", search(tel));
        }

        String sql = "SELECT * FROM contacts WHERE ";
        sql += StringUtils.join(map.keySet(), " like ? or ") + " like ?";
        return listQuery(sql, Contact.class, map.values().toArray());
    }

    private String search(String value) {
        return "%" + value.trim().toLowerCase() + "%";
    }

    public Contact getContact(int id) throws SQLException {
        String sql = "SELECT * FROM contacts WHERE id=?";
        return query(sql, Contact.class, id);
    }

    public void update(Contact contact) throws SQLException {
        String sql = "UPDATE contacts set " +
                StringUtils.join(CONTACT_COLUMNS, "=?,") +
                "=? WHERE id=?";
        Object[] values = {contact.first,
                contact.last,
                contact.address,
                contact.postcode,
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
        Object[] values = {contact.first,
                contact.last,
                contact.address,
                contact.postcode,
                contact.telephone.number
        };
        update(sql, values);
    }


    @Override
    public <T> T toBean(ResultSet rs, Class<T> type) throws SQLException {
        return (T)new Contact(rs.getInt("id"), rs.getString("first"), rs.getString("last"), rs.getString("address"), rs.getString("postcode"), new Telephone(rs.getString("telephone")));
    }
}
