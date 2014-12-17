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
    public static final String[] CONTACT_COLUMNS = {"name","address","postcode","telephone"};

    public ContactDao(DataSource dataSource) {
        super(dataSource);
    }

    public List<Contact> getContacts() throws SQLException {
        String sql = "SELECT * FROM contacts WHERE active = true";
        return listQuery(sql, Contact.class);
    }

    public List<Contact> find(String text) throws SQLException {
        Map<String, String> map = new LinkedHashMap<>();
        text = text.toLowerCase();
        map.put("lower(name)", search(text));
        map.put("lower(address)", search(text));
        map.put("lower(postcode)", search(text));
        map.put("lower(telephone)", search(text));

        String sql = "SELECT * FROM contacts WHERE active = true and (";
        sql += StringUtils.join(map.keySet(), " like ? or ") + " like ?)";
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
        Object[] values = {contact.getName(),
                contact.getAddress(),
                contact.getPostcode(),
                contact.getTelephone().number,
                contact.getId()
        };
        update(sql, values);
    }

    public void save(Contact contact) throws SQLException {
        String sql = "INSERT INTO contacts (" +
                StringUtils.join(CONTACT_COLUMNS,",") + ") " +
                "VALUES (?" +
                StringUtils.repeat(",?", CONTACT_COLUMNS.length - 1) +
                ")";
        Object[] values = {contact.getName(),
                contact.getAddress(),
                contact.getPostcode(),
                contact.getTelephone() != null ? contact.getTelephone().number : ""
        };
        update(sql, values);
    }

    @Override
    public <T> T toBean(ResultSet rs, Class<T> type) throws SQLException {
        return (T)new Contact(rs.getInt("id"), rs.getString("name"), rs.getString("address"), rs.getString("postcode"), new Telephone(rs.getString("telephone")));
    }

    public void delete(int id) throws SQLException {
        String sql = "UPDATE contacts set active = false WHERE id=?";
        Object[] values = {id};
        update(sql, values);
    }
}
