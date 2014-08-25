package net.astechdesign.clients.repo;

import net.astechdesign.clients.model.Address;
import net.astechdesign.clients.model.Contact;
import net.astechdesign.clients.model.Name;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactRowProcessor extends DaoRowProcessor {

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
