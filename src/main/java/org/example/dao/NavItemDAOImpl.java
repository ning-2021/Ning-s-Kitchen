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
        String getAllNavItemsSql = "SELECT a.id, a.name, json_agg(json_build_object('id', b.id, 'name', b.name)) AS agg " +
                                   "FROM types a " +
                                   "LEFT JOIN tags b " +
                                   "ON a.id = b.type_id " +
                                   "GROUP BY a.id, a.name ";
        return jdbc.query(getAllNavItemsSql, new NavItemMapper());
    }
}