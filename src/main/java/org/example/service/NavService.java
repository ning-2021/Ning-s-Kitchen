package org.example.service;

import org.example.dao.NavItemDAOImpl;
import org.example.model.NavItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NavService {

    private final NavItemDAOImpl navItemDAOImpl;

    public NavService(NavItemDAOImpl navItemDAOImpl) {
        this.navItemDAOImpl = navItemDAOImpl;
    }

    public List<NavItem> findNavItems() {
        return navItemDAOImpl.getAllNavItems();
    }
}
