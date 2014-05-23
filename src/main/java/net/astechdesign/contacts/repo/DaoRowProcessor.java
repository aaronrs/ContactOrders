package net.astechdesign.contacts.repo;

import org.apache.commons.dbutils.BasicRowProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoRowProcessor extends BasicRowProcessor {

    @Override
    public <T> List<T> toBeanList(ResultSet rs, Class<T> type) throws SQLException {
        List<T> list = new ArrayList<>();
        while (rs.next()) {
            list.add(toBean(rs, type));
        }
        return list;
    }
}
