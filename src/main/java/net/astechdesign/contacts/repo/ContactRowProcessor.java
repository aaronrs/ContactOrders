package net.astechdesign.contacts.repo;

import net.astechdesign.contacts.model.Address;
import net.astechdesign.contacts.model.Contact;
import net.astechdesign.contacts.model.Name;
import org.apache.commons.dbutils.BasicRowProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactRowProcessor extends BasicRowProcessor {
    @Override
    public <T> List<T> toBeanList(ResultSet rs, Class<T> type) throws SQLException {
        List<Contact> contactList = new ArrayList<>();
        while (rs.next()) {
            contactList.add(toBean(rs, Contact.class));
        }
        return (List<T>)contactList;
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
                rs.getString("postcode"),
                rs.getString("telephone")
                );
        return (T)new Contact(rs.getInt("id"), name, address);
    }
}
