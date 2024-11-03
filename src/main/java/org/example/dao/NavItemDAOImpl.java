package org.example.dao;

import org.example.model.NavItem;
import org.example.model.NavItemMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class NavItemDAOImpl implements NavItemDao {
    JdbcTemplate jdbc;

    public NavItemDAOImpl(DataSource dataSource) {
        jdbc = new JdbcTemplate(dataSource);
    }

    public List<NavItem> getAllNavItems() {
        String getAllNavItemsSql = "SELECT * FROM types";
        return jdbc.query(getAllNavItemsSql, new NavItemMapper());
    }
}
