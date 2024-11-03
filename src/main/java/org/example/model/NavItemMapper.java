package org.example.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class NavItemMapper implements RowMapper<NavItem> {
    public NavItem mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        NavItem navItem = new NavItem();
        navItem.setId(resultSet.getInt("id"));
        navItem.setName(resultSet.getString("name"));
        return navItem;
    }

}
