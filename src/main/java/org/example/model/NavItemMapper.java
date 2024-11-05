package org.example.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.jdbc.core.RowMapper;

public class NavItemMapper implements RowMapper<NavItem> {
    public NavItem mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        NavItem navItem = new NavItem();
        navItem.setId(resultSet.getInt("id"));
        navItem.setName(resultSet.getString("name"));

        // parse the JSON string to List<DropdownItem>
        String jsonString = resultSet.getString("agg");
        List<DropdownItem> dropdownItems = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(jsonString);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            int dropdownId = obj.isNull("id") ? 0 : obj.getInt("id");
            String dropdownName = obj.isNull("name") ? "" : obj.getString("name");
            dropdownItems.add(new DropdownItem(dropdownId, dropdownName));
        }
        navItem.setDropdownItems(dropdownItems);

        return navItem;
    }

}
